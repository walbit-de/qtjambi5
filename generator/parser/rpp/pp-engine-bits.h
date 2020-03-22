/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
** Copyright 2005 Roberto Raggi <roberto@kdevelop.org>
**
** This file is part of Qt Jambi.
**
** ** $BEGIN_LICENSE$
** Commercial Usage
** Licensees holding valid Qt Commercial licenses may use this file in
** accordance with the Qt Commercial License Agreement provided with the
** Software or, alternatively, in accordance with the terms contained in
** a written agreement between you and Nokia.
**
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
**
** If you are unsure which license is appropriate for your use, please
** contact the sales department at qt-sales@nokia.com.
** $END_LICENSE$

**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

#ifndef PP_ENGINE_BITS_H
#define PP_ENGINE_BITS_H

#include <string>
#include <vector>
#include <cstdio>

#include <QFile>
#include <QDebug>

#include <sys/stat.h>

#include "debuglog.h"
#include "pp-internal.h"
#include "pp-symbol.h"
#include "pp-cctype.h"
#include "pp-macro-expander.h"
#include "pp-environment.h"
#include "pp-scanner.h"

class QByteArray;
namespace rpp {

    enum PP_DIRECTIVE_TYPE {
        PP_UNKNOWN_DIRECTIVE,
        PP_DEFINE,
        PP_INCLUDE,
        PP_INCLUDE_NEXT,
        PP_ELIF,
        PP_ELSE,
        PP_ENDIF,
        PP_IF,
        PP_IFDEF,
        PP_IFNDEF,
        PP_UNDEF,
        PP_PRAGMA,
        PP_ERROR,
        PP_WARNING
    };

    enum INCLUDE_POLICY {
        INCLUDE_GLOBAL,
        INCLUDE_LOCAL
    };

    enum TOKEN_TYPE {
        TOKEN_NUMBER = 1000,
        TOKEN_UNUMBER,
        TOKEN_IDENTIFIER,
        TOKEN_DEFINED,
        TOKEN_LT_LT,
        TOKEN_LT_EQ,
        TOKEN_GT_GT,
        TOKEN_GT_EQ,
        TOKEN_EQ_EQ,
        TOKEN_NOT_EQ,
        TOKEN_OR_OR,
        TOKEN_AND_AND,
    };

    struct Value {
        enum Kind {
            Kind_Long,
            Kind_ULong,
        };

        Kind kind;

        union {
            long l;
            unsigned long ul;
        };

        bool is_ulong() const { return kind == Kind_ULong; }

        void set_ulong(unsigned long v) {
            ul = v;
            kind = Kind_ULong;
        }

        void set_long(long v) {
            l = v;
            kind = Kind_Long;
        }

        bool is_zero() const { return l == 0; }

#define PP_DEFINE_BIN_OP(name, op) \
  inline Value &name (const Value &other) \
  { \
    if (is_ulong () || other.is_ulong ()) \
      set_ulong (ul op other.ul); \
    else \
      set_long (l op other.l); \
    return *this; \
  }

        PP_DEFINE_BIN_OP(op_add, +)
        PP_DEFINE_BIN_OP(op_sub, -)
        PP_DEFINE_BIN_OP(op_mult, *)
        PP_DEFINE_BIN_OP(op_div, /)
        PP_DEFINE_BIN_OP(op_mod, %)
        PP_DEFINE_BIN_OP(op_lhs, <<)
        PP_DEFINE_BIN_OP(op_rhs, >>)
        PP_DEFINE_BIN_OP(op_lt, <)
        PP_DEFINE_BIN_OP(op_gt, >)
        PP_DEFINE_BIN_OP(op_le, <=)
        PP_DEFINE_BIN_OP(op_ge, >=)
        PP_DEFINE_BIN_OP(op_eq, ==)
        PP_DEFINE_BIN_OP(op_ne, !=)
        PP_DEFINE_BIN_OP(op_bit_and, &)
        PP_DEFINE_BIN_OP(op_bit_or, |)
        PP_DEFINE_BIN_OP(op_bit_xor, ^)
        PP_DEFINE_BIN_OP(op_and, &&)
        PP_DEFINE_BIN_OP(op_or, ||)

#undef PP_DEFINE_BIN_OP
    };

    class pp {

            union {
                long token_value;
                unsigned long token_uvalue;
                std::string *token_text;
            };

        public:

            void push_include_path(const std::string& path);

            pp(pp_environment &__env);


            /**
             * Loops though the given file and writes preprocessed
             * data to _OutputIterator result.
             */
            template <typename _InputIterator, typename _OutputIterator>
            void operator()(_InputIterator first, _InputIterator last, _OutputIterator result) {
#ifndef PP_NO_SMART_HEADER_PROTECTION
                std::string protection;
                protection.reserve(255);
                pp_fast_string tmp(protection.c_str(), protection.size());

                if (find_header_protection(first, last, &protection)
                        && env.resolve(&tmp) != 0) {
                    std::cerr << "** DEBUG found header protection:" << protection << std::endl;
                    return;
                }
#endif

                env.current_line = 1;
                char buffer[512];

                while (true) {
                    first = skip_blanks(first, last);
                    env.current_line += skip_blanks.lines;

                    if (first == last)
                        break;
                    else if (*first == '#') {
                        first = skip_blanks(++first, last);
                        env.current_line += skip_blanks.lines;

                        _InputIterator identifier_end = skip_identifier(first, last);
                        env.current_line += skip_identifier.lines;
                        std::size_t size = identifier_end - first;

                        assert(size < 512);
                        char *copy = buffer;
                        std::copy(first, identifier_end, copy);
                        copy[size] = '\0';

                        identifier_end = skip_blanks(identifier_end, last);
                        first = skip(identifier_end, last);

                        int was = env.current_line;
                        (void) handle_directive(buffer, size, identifier_end, first, result);

                        if (env.current_line != was) {
                            env.current_line = was;
                            _PP_internal::output_line(env.current_file, env.current_line, result);
                        }
                    } else if (*first == '\n') {
                        // ### compress the line
                        *result++ = *first++;
                        ++env.current_line;
                    } else if (skipping()) {
                        first = skip(first, last);
                    } else {
                        _PP_internal::output_line(env.current_file, env.current_line, result);
                        first = expand_macro(first, last, result);
                        env.current_line += expand_macro.lines;

                        if (expand_macro.generated_lines)
                            _PP_internal::output_line(env.current_file, env.current_line, result);
                    }
                }
            }

            /**
             * Opens given file and passes it to file(FILE*, _OutputIterator).
             */
            template <typename _OutputIterator>
            void file(std::string const &filename, _OutputIterator __result) {
                qDebug() << "Reading file:" << filename.c_str();
                FILE *fp = std::fopen(filename.c_str(), "rb");
                if (fp != 0) {
                    std::string was = env.current_file;
                    env.current_file = filename;
                    file(fp, __result, filename.c_str());
                    env.current_file = was;
                } else {
                    std::cerr << "** WARNING file ``" << filename << " not found!" << std::endl;
                }
            }

            /**
             * Reads contents of given file and passes contents of file
             * to operator(_InputIterator, _InputIterator, _OutputIterator).
             */
            template <typename _OutputIterator>
            void file(FILE *fp, _OutputIterator result, const char *filename) {
                assert(fp != 0);

#if defined (HAVE_MMAP)
                struct stat st;
                fstat(FILENO(fp), &st);
                std::size_t size = st.st_size;
                char *buffer = 0;
                buffer = (char *) ::mmap(0, size, PROT_READ, MAP_SHARED, FILENO(fp), 0);
                fclose(fp);
                if (!buffer || buffer == (char*) - 1)
                    return;
                this->operator()(buffer, buffer + size, __result);
                ::munmap(buffer, size);
#else
                QFile file;

                if (!file.open(fp, QIODevice::ReadOnly)) { //succeeds always
                    std::cout << "pp-engine-bits.h[file(FILE*, _OutputIterator)]: Failed to open file for read" << std::endl;
                    exit(1);
                }

                QByteArray data = QByteArray();
                //if(filename) data+="STARTOFINCLUDE(\""+QByteArray(filename)+"\");\n";
                data+=file.readAll();
                data += "\nENDOFINCLUDE;\n";
                if (data.isEmpty()) {
                    std::cout << "pp-engine-bits.h[file(FILE*, _OutputIterator)]: Failed to read the file" << std::endl;
                    exit(1);
                }
                file.close();
                std::fclose(fp);
                this->operator()((const char *)data.constData(), (const char *)(data.constData() + data.size()), result);
                /*
                 * NOTE: the code is commented out for it seems to have some problems...
                 * Too lazy to fix it, so now there is Qt based solution for it
                 * The Qt solution also does more ... secure checks ... maybe? Whatever.
                std::string buffer;
                while ( !std::feof ( fp ) ) {
                    char tmp[1024];
                    int read = ( int ) std::fread ( tmp, sizeof ( char ), 1023, fp );
                    tmp[read] = '\0';
                    buffer += tmp;
                }
                std::fclose ( fp );
                this->operator () ( buffer.c_str(), buffer.c_str() + buffer.size(), result );*/
#endif
            }

            template <typename _InputIterator>
            bool find_header_protection(_InputIterator __first, _InputIterator __last, std::string *__prot) {
                int was = env.current_line;

                while (__first != __last) {
                    if (pp_isspace(*__first)) {
                        if (*__first == '\n')
                            ++env.current_line;

                        ++__first;
                    } else if (_PP_internal::comment_p(__first, __last)) {
                        __first = skip_comment_or_divop(__first, __last);
                        env.current_line += skip_comment_or_divop.lines;
                    } else if (*__first == '#') {
                        __first = skip_blanks(++__first, __last);
                        env.current_line += skip_blanks.lines;

                        if (__first != __last && *__first == 'i') {
                            _InputIterator __begin = __first;
                            __first = skip_identifier(__begin, __last);
                            env.current_line += skip_identifier.lines;

                            std::string __directive(__begin, __first);

                            if (__directive == "ifndef") {
                                __first = skip_blanks(__first, __last);
                                env.current_line += skip_blanks.lines;

                                __begin = __first;
                                __first = skip_identifier(__first, __last);
                                env.current_line += skip_identifier.lines;

                                if (__begin != __first && __first != __last) {
                                    __prot->assign(__begin, __first);
                                    return true;
                                }
                            }
                        }
                        break;
                    } else
                        break;
                }

                env.current_line = was;
                return false;
            }


            std::vector<std::string> include_paths;
            pp_skip_blanks skip_blanks;
            pp_skip_comment_or_divop skip_comment_or_divop;
            enum { MAX_LEVEL = 512 };
            int _M_skipping[MAX_LEVEL];
            int _M_true_test[MAX_LEVEL];
            int iflevel;
            int verbose;

        private:
            pp_environment &env;

            pp_macro_expander expand_macro;
            pp_skip_identifier skip_identifier;
            pp_skip_number skip_number;
            std::string _M_current_text;

            std::string fix_file_path(std::string const &filename) const;

            bool is_absolute(std::string const &filename) const;

            bool file_isdir(std::string const &__filename) const;

            bool file_exists(std::string const &__filename) const;

            std::back_insert_iterator<std::vector<std::string> > include_paths_inserter();

            std::vector<std::string>::iterator include_paths_begin();

            std::vector<std::string>::iterator include_paths_end();

            std::vector<std::string>::const_iterator include_paths_begin() const;

            std::vector<std::string>::const_iterator include_paths_end() const;


            bool test_if_level();

            int skipping() const;

            /**
             * Returns preprocessor directive type, PP_DIRECTIVE_TYPE,
             * p_directive corresponds to.
             */
            PP_DIRECTIVE_TYPE find_directive(const char* p_directive, std::size_t p_size) const;

            /**
             * Finds correct include file from include paths or given data and returns
             * FILE pointer to that file.
             */
            FILE *find_include_file(std::string const &__input_filename, std::string *__filepath,
                                    INCLUDE_POLICY __include_policy, bool __skip_current_path) ;

            /**
             *
             */
            template <typename _InputIterator, typename _OutputIterator>
            _InputIterator handle_directive(char const *given_directive,
                                            std::size_t size,
                                            _InputIterator first,
                                            _InputIterator last,
                                            _OutputIterator result) {

                first = skip_blanks(first, last);

                PP_DIRECTIVE_TYPE directive = find_directive(given_directive, size);
                switch (directive) {
                    case PP_DEFINE:
                        if (! skipping())
                            return handle_define(first, last);
                        break;

                    case PP_INCLUDE:
                    case PP_INCLUDE_NEXT:
                        if (! skipping())
                            return handle_include(directive == PP_INCLUDE_NEXT, first, last, result);
                        break;

                    case PP_UNDEF:
                        if (! skipping())
                            return handle_undef(first, last);
                        break;

                    case PP_ELIF:
                        return handle_elif(first, last);

                    case PP_ELSE:
                        return handle_else(first, last);

                    case PP_ENDIF:
                        return handle_endif(first, last);

                    case PP_IF:
                        return handle_if(first, last);

                    case PP_IFDEF:
                        return handle_ifdef(false, first, last);

                    case PP_IFNDEF:
                        return handle_ifdef(true, first, last);

                    default:
                        break;
                }

                return first;
            }

            template <typename _InputIterator, typename _OutputIterator>
            _InputIterator handle_include(bool skip_current_path, _InputIterator first, _InputIterator last,
                                          _OutputIterator result) {

                if (pp_isalpha(*first) || *first == '_') {
                    pp_macro_expander expand_include(env);
                    std::string name;
                    name.reserve(255);
                    expand_include(first, last, std::back_inserter(name));
                    std::string::iterator it = skip_blanks(name.begin(), name.end());
                    assert(it != name.end() && (*it == '<' || *it == '"'));
                    handle_include(skip_current_path, it, name.end(), result);
                    return first;
                }

                assert(*first == '<' || *first == '"');
                int quote = (*first == '"') ? '"' : '>';
                ++first;

                _InputIterator end_name = first;
                for (; end_name != last; ++end_name) {
                    assert(*end_name != '\n');

                    if (*end_name == quote)
                        break;
                }

                std::string filename(first, end_name);
                //qDebug()<<filename.c_str(); //file name of include parsed

#ifdef PP_OS_WIN
                std::replace(filename.begin(), filename.end(), '/', '\\');
#endif

                std::string filepath;
                FILE *fp = find_include_file(filename, &filepath, quote == '>' ?
                                             INCLUDE_GLOBAL : INCLUDE_LOCAL, skip_current_path);

#if defined (PP_HOOK_ON_FILE_INCLUDED)
                PP_HOOK_ON_FILE_INCLUDED(env.current_file, fp ? filepath : filename, fp);
#endif

                if (fp != 0) {
                    std::string old_file = env.current_file;
                    env.current_file = filepath;
                    int __saved_lines = env.current_line;

                    env.current_line = 1;
                    //output_line (env.current_file, 1, __result);

                    file(fp, result, filename.c_str());

                    // restore the file name and the line position
                    env.current_file = old_file;
                    env.current_line = __saved_lines;

                    // sync the buffer
                    _PP_internal::output_line(env.current_file, env.current_line, result);
                } else {
                    if((verbose & DEBUGLOG_INCLUDE_ERRORS) != 0) {
                        std::cerr << "*** WARNING " << env.current_file << ":" << env.current_line << "  " <<
                            (quote != '>' ? '"' : '<') << filename << (quote != '>' ? '"' : '>') << ": No such file or directory" << std::endl;
                    }
                }

                return first;
            }

            template <typename _InputIterator>
            _InputIterator handle_define(_InputIterator __first, _InputIterator __last) {
                pp_macro macro;
#if defined (PP_WITH_MACRO_POSITION)
                macro.file = pp_symbol::get(env.current_file);
#endif
                std::string definition;

                __first = skip_blanks(__first, __last);
                _InputIterator end_macro_name = skip_identifier(__first, __last);
                pp_fast_string const *macro_name = pp_symbol::get(__first, end_macro_name);
                __first = end_macro_name;

                if (__first != __last && *__first == '(') {
                    macro.function_like = true;
                    macro.formals.reserve(5);

                    __first = skip_blanks(++__first, __last);    // skip '('
                    _InputIterator arg_end = skip_identifier(__first, __last);
                    if (__first != arg_end)
                        macro.formals.push_back(pp_symbol::get(__first, arg_end));

                    __first = skip_blanks(arg_end, __last);

                    if (*__first == '.') {
                        macro.variadics = true;
                        while (*__first == '.')
                            ++__first;
                    }

                    while (__first != __last && *__first == ',') {
                        __first = skip_blanks(++__first, __last);

                        arg_end = skip_identifier(__first, __last);
                        if (__first != arg_end)
                            macro.formals.push_back(pp_symbol::get(__first, arg_end));

                        __first = skip_blanks(arg_end, __last);

                        if (*__first == '.') {
                            macro.variadics = true;
                            while (*__first == '.')
                                ++__first;
                        }
                    }

                    assert(*__first == ')');
                    ++__first;
                }

                __first = skip_blanks(__first, __last);

                while (__first != __last && *__first != '\n') {
                    if (*__first == '/') {
                        __first = skip_comment_or_divop(__first, __last);
                        env.current_line += skip_comment_or_divop.lines;
                    }

                    if (*__first == '\\') {
                        _InputIterator __begin = __first;
                        __begin = skip_blanks(++__begin, __last);

                        if (__begin != __last && *__begin == '\n') {
                            ++macro.lines;
                            __first = skip_blanks(++__begin, __last);
                            definition += ' ';
                            continue;
                        }
                    }

                    definition += *__first++;
                }

                macro.definition = pp_symbol::get(definition);
                if((verbose & DEBUGLOG_DEFINE) != 0) {
                    std::cout << "#define " << std::string(macro_name->begin(), macro_name->end()) <<
                        " " << std::string(macro.definition->begin(), macro.definition->end()) << std::endl;
                }
                env.bind(macro_name, macro);

                return __first;
            }

            /**
             * Invokes different skipping functions based the on first.
             */
            template <typename _InputIterator>
            _InputIterator skip(_InputIterator first, _InputIterator last) {
                pp_skip_string_literal skip_string_literal;
                pp_skip_char_literal skip_char_literal;

                while (first != last && *first != '\n') {
                    if (*first == '/') {
                        first = skip_comment_or_divop(first, last);
                        env.current_line += skip_comment_or_divop.lines;
                    } else if (*first == '"') {
                        first = skip_string_literal(first, last);
                        env.current_line += skip_string_literal.lines;
                    } else if (*first == '\'') {
                        first = skip_char_literal(first, last);
                        env.current_line += skip_char_literal.lines;
                    } else if (*first == '\\') {
                        first = skip_blanks(++first, last);
                        env.current_line += skip_blanks.lines;

                        if (first != last && *first == '\n') {
                            ++first;
                            ++env.current_line;
                        }
                    } else
                        ++first;
                }

                return first;
            }


            template <typename _InputIterator>
            _InputIterator eval_primary(_InputIterator __first, _InputIterator __last, Value *result) {
                bool expect_paren = false;
                int token;
                _InputIterator next;
                __first = next_token(__first, __last, &token);

                switch (token) {
                    case TOKEN_NUMBER:
                        result->set_long(token_value);
                        break;

                    case TOKEN_UNUMBER:
                        result->set_ulong(token_uvalue);
                        break;

                    case TOKEN_DEFINED:
                        __first = next_token(__first, __last, &token);

                        if (token == '(') {
                            expect_paren = true;
                            __first = next_token(__first, __last, &token);
                        }

                        if (token != TOKEN_IDENTIFIER) {
                            std::cerr << "** WARNING expected ``identifier'' found:" << char(token) << std::endl;
                            result->set_long(0);
                            break;
                        }

                        result->set_long(env.resolve(token_text->c_str(), token_text->size()) != 0);

                        next_token(__first, __last, &token);    // skip '('

                        if (expect_paren) {
                            next = next_token(__first, __last, &token);
                            if (token != ')')
                                std::cerr << "** WARNING expected ``)''" << std::endl;
                            else
                                __first = next;
                        }
                        break;

                    case TOKEN_IDENTIFIER:
                        result->set_long(0);
                        break;

                    case '-':
                        __first = eval_primary(__first, __last, result);
                        result->set_long(- result->l);
                        return __first;

                    case '+':
                        __first = eval_primary(__first, __last, result);
                        return __first;

                    case '!':
                        __first = eval_primary(__first, __last, result);
                        result->set_long(result->is_zero());
                        return __first;

                    case '(':
                        __first = eval_constant_expression(__first, __last, result);
                        next = next_token(__first, __last, &token);

                        if(token == TOKEN_IDENTIFIER) {
                        	next = next_token(next, __last, &token);
                        	next = next_token(next, __last, &token);
                        	next = next_token(next, __last, &token);
                        	__first = next;
                        	//break;
                        }
                        if (token != ')') {
                            pp_fast_string const *snippet = pp_symbol::get(__first, __last);
                            std::cerr << "** WARNING expected ``)'' = " << token << " " << std::string(snippet->begin(), snippet->end()) <<
                                         " at " << env.current_file << ":" << env.current_line << std::endl;
                        } else {
                            __first = next;
                        }
                        break;

                    default:
                        result->set_long(0);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_multiplicative(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_primary(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '*' || token == '/' || token == '%') {
                    Value value;
                    __first = eval_primary(next, __last, &value);

                    if (token == '*')
                        result->op_mult(value);
                    else if (token == '/') {
                        if (value.is_zero()) {
                            std::cerr << "** WARNING division by zero" << std::endl;
                            result->set_long(0);
                        } else
                            result->op_div(value);
                    } else {
                        if (value.is_zero()) {
                            std::cerr << "** WARNING division by zero" << std::endl;
                            result->set_long(0);
                        } else
                            result->op_mod(value);
                    }
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_additive(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_multiplicative(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '+' || token == '-') {
                    Value value;
                    __first = eval_multiplicative(next, __last, &value);

                    if (token == '+')
                        result->op_add(value);
                    else
                        result->op_sub(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_shift(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_additive(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == TOKEN_LT_LT || token == TOKEN_GT_GT) {
                    Value value;
                    __first = eval_additive(next, __last, &value);

                    if (token == TOKEN_LT_LT)
                        result->op_lhs(value);
                    else
                        result->op_rhs(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_relational(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_shift(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '<'
                        || token == '>'
                        || token == TOKEN_LT_EQ
                        || token == TOKEN_GT_EQ) {
                    Value value;
                    __first = eval_shift(next, __last, &value);

                    switch (token) {
                        default:
                            assert(0);
                            break;

                        case '<':
                            result->op_lt(value);
                            break;

                        case '>':
                            result->op_gt(value);
                            break;

                        case TOKEN_LT_EQ:
                            result->op_le(value);
                            break;

                        case TOKEN_GT_EQ:
                            result->op_ge(value);
                            break;
                    }
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_equality(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_relational(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == TOKEN_EQ_EQ || token == TOKEN_NOT_EQ) {
                    Value value;
                    __first = eval_relational(next, __last, &value);

                    if (token == TOKEN_EQ_EQ)
                        result->op_eq(value);
                    else
                        result->op_ne(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_and(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_equality(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '&') {
                    Value value;
                    __first = eval_equality(next, __last, &value);
                    result->op_bit_and(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_xor(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_and(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '^') {
                    Value value;
                    __first = eval_and(next, __last, &value);
                    result->op_bit_xor(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_or(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_xor(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == '|') {
                    Value value;
                    __first = eval_xor(next, __last, &value);
                    result->op_bit_or(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_logical_and(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_or(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == TOKEN_AND_AND) {
                    Value value;
                    __first = eval_or(next, __last, &value);
                    result->op_and(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_logical_or(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_logical_and(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                while (token == TOKEN_OR_OR) {
                    Value value;
                    __first = eval_logical_and(next, __last, &value);
                    result->op_or(value);
                    next = next_token(__first, __last, &token);
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_constant_expression(_InputIterator __first, _InputIterator __last, Value *result) {
                __first = eval_logical_or(__first, __last, result);

                int token;
                _InputIterator next = next_token(__first, __last, &token);

                if (token == '?') {
                    Value left_value;
                    __first = eval_constant_expression(next, __last, &left_value);
                    __first = skip_blanks(__first, __last);

                    __first = next_token(__first, __last, &token);
                    if (token == ':') {
                        Value right_value;
                        __first = eval_constant_expression(__first, __last, &right_value);

                        *result = !result->is_zero() ? left_value : right_value;
                    } else {
                        std::cerr << "** WARNING expected ``:'' = " << int (token) << std::endl;
                        *result = left_value;
                    }
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator eval_expression(_InputIterator __first, _InputIterator __last, Value *result) {
                return __first = eval_constant_expression(skip_blanks(__first, __last), __last, result);
            }

            template <typename _InputIterator>
            _InputIterator handle_if(_InputIterator __first, _InputIterator __last) {
                if (test_if_level()) {
                    pp_macro_expander expand_condition(env);
                    std::string condition;
                    condition.reserve(255);
                    expand_condition(skip_blanks(__first, __last), __last, std::back_inserter(condition));

                    Value result;
                    result.set_long(0);
                    eval_expression(condition.c_str(), condition.c_str() + condition.size(), &result);

                    _M_true_test[iflevel] = !result.is_zero();
                    _M_skipping[iflevel] = result.is_zero();
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator handle_else(_InputIterator __first, _InputIterator /*__last*/) {
                if (iflevel == 0 && !skipping()) {
                    std::cerr << "** WARNING #else without #if" << std::endl;
                } else if (iflevel > 0 && _M_skipping[iflevel - 1]) {
                    _M_skipping[iflevel] = true;
                } else {
                    _M_skipping[iflevel] = _M_true_test[iflevel];
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator handle_elif(_InputIterator __first, _InputIterator __last) {
                assert(iflevel > 0);

                if (iflevel == 0 && !skipping()) {
                    std::cerr << "** WARNING #else without #if" << std::endl;
                } else if (!_M_true_test[iflevel] && !_M_skipping[iflevel - 1]) {
                    Value result;
                    __first = eval_expression(__first, __last, &result);
                    _M_true_test[iflevel] = !result.is_zero();
                    _M_skipping[iflevel] = result.is_zero();
                } else {
                    _M_skipping[iflevel] = true;
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator handle_endif(_InputIterator __first, _InputIterator /*__last*/) {
                if (iflevel == 0 && !skipping()) {
                    std::cerr << "** WARNING #endif without #if" << std::endl;
                } else {
                    _M_skipping[iflevel] = 0;
                    _M_true_test[iflevel] = 0;

                    --iflevel;
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator handle_ifdef(bool check_undefined, _InputIterator __first, _InputIterator __last) {
                if (test_if_level()) {
                    _InputIterator end_macro_name = skip_identifier(__first, __last);

                    std::size_t __size;
#if defined(__SUNPRO_CC)
                    std::distance(__first, end_macro_name, __size);
#else
                    __size = std::distance(__first, end_macro_name);
#endif
                    assert(__size < 256);

                    char __buffer [256];
                    std::copy(__first, end_macro_name, __buffer);

                    bool value = env.resolve(__buffer, __size) != 0;

                    __first = end_macro_name;

                    if (check_undefined)
                        value = !value;

                    _M_true_test[iflevel] = value;
                    _M_skipping[iflevel] = !value;
                }

                return __first;
            }

            template <typename _InputIterator>
            _InputIterator handle_undef(_InputIterator __first, _InputIterator __last) {
                __first = skip_blanks(__first, __last);
                _InputIterator end_macro_name = skip_identifier(__first, __last);
                assert(end_macro_name != __first);

                std::size_t __size;
#if defined(__SUNPRO_CC)
                std::distance(__first, end_macro_name, __size);
#else
                __size = std::distance(__first, end_macro_name);
#endif

                assert(__size < 256);

                char __buffer [256];
                std::copy(__first, end_macro_name, __buffer);

                pp_fast_string const __tmp(__buffer, __size);
                if((verbose & DEBUGLOG_UNDEF) != 0)
                    std::cout << "#undef " << std::string(__tmp.begin(), __tmp.end()) << std::endl;
                env.unbind(&__tmp);

                __first = end_macro_name;

                return __first;
            }

            template <typename _InputIterator>
            char peek_char(_InputIterator __first, _InputIterator __last) {
                if (__first == __last)
                    return 0;

                return *++__first;
            }

            template <typename _InputIterator>
            _InputIterator next_token(_InputIterator __first, _InputIterator __last, int *kind) {
                __first = skip_blanks(__first, __last);

                if (__first == __last) {
                    *kind = 0;
                    return __first;
                }

                char ch = *__first;
                char ch2 = peek_char(__first, __last);

                switch (ch) {
                    case '/':
                        if (ch2 == '/' || ch2 == '*') {
                            __first = skip_comment_or_divop(__first, __last);
                            return next_token(__first, __last, kind);
                        }
                        ++__first;
                        *kind = '/';
                        break;

                    case '<':
                        ++__first;
                        if (ch2 == '<') {
                            ++__first;
                            *kind = TOKEN_LT_LT;
                        } else if (ch2 == '=') {
                            ++__first;
                            *kind = TOKEN_LT_EQ;
                        } else
                            *kind = '<';

                        return __first;

                    case '>':
                        ++__first;
                        if (ch2 == '>') {
                            ++__first;
                            *kind = TOKEN_GT_GT;
                        } else if (ch2 == '=') {
                            ++__first;
                            *kind = TOKEN_GT_EQ;
                        } else
                            *kind = '>';

                        return __first;

                    case '!':
                        ++__first;
                        if (ch2 == '=') {
                            ++__first;
                            *kind = TOKEN_NOT_EQ;
                        } else
                            *kind = '!';

                        return __first;

                    case '=':
                        ++__first;
                        if (ch2 == '=') {
                            ++__first;
                            *kind = TOKEN_EQ_EQ;
                        } else
                            *kind = '=';

                        return __first;

                    case '|':
                        ++__first;
                        if (ch2 == '|') {
                            ++__first;
                            *kind = TOKEN_OR_OR;
                        } else
                            *kind = '|';

                        return __first;

                    case '&':
                        ++__first;
                        if (ch2 == '&') {
                            ++__first;
                            *kind = TOKEN_AND_AND;
                        } else
                            *kind = '&';

                        return __first;

                    default:
                        if (pp_isalpha(ch) || ch == '_') {
                            _InputIterator end = skip_identifier(__first, __last);
                            _M_current_text.assign(__first, end);

                            token_text = &_M_current_text;
                            __first = end;

                            if (*token_text == "defined")
                                *kind = TOKEN_DEFINED;
                            else
                                *kind = TOKEN_IDENTIFIER;
                        } else if (pp_isdigit(ch)) {
                            _InputIterator end = skip_number(__first, __last);
                            std::string __str(__first, __last);
                            char ch = __str [__str.size() - 1];
                            if (ch == 'u' || ch == 'U') {
                                token_uvalue = strtoul(__str.c_str(), 0, 0);
                                *kind = TOKEN_UNUMBER;
                            } else {
                                token_value = strtol(__str.c_str(), 0, 0);
                                *kind = TOKEN_NUMBER;
                            }
                            __first = end;
                        } else
                            *kind = *__first++;
                }

                return __first;
            }

    };

} // namespace rpp

#endif // PP_ENGINE_BITS_H
