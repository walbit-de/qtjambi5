/****************************************************************************
**
** This file is part of Qt Jambi.
**
** ** $BEGIN_LICENSE$
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
** $END_LICENSE$
**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

#ifndef MODIFICATION_H_
#define MODIFICATION_H_

#include <QHash>
#include "typesystem.h"
#include "codesnip.h"

struct ReferenceCount;

struct ArgumentModification {
    ArgumentModification(int idx) :
            removed_default_expression(false),
            removed(false),
            no_null_pointers(false),
            index(idx) {}

    //! Should the default expression be removed?
uint removed_default_expression : 1;
uint removed : 1;
uint no_null_pointers : 1;
uint reset_after_use : 1;

    //! The index of this argument
    int index;

    //! Reference count flags for this argument
    QList<ReferenceCount> referenceCounts;

    //! The text given for the new type of the argument
    QString modified_type;

    QString replace_value;

    //! The code to be used to construct a return value when no_null_pointers is true and
    //! the returned value is null. If no_null_pointers is true and this string is
    //! empty, then the base class implementation will be used (or a default construction
    //! if there is no implementation)
    QString null_pointer_default_value;

    //! The text of the new default expression of the argument
    QString replaced_default_expression;

    //! The new definition of ownership for a specific argument
    QHash<TypeSystem::Language, TypeSystem::Ownership> ownerships;

    //! Different conversion rules
    CodeSnipList conversion_rules;
};

struct Modification {
    enum Modifiers {
        Private =               0x00000001,
        Protected =             0x00000002,
        Public =                0x00000003,
        Friendly =              0x00000004,
        AccessModifierMask =    0x0000000f,

        Final =                 0x00000010,
        NonFinal =              0x00000020,
        NativeDeclFinal =       0x00100000,
        FinalMask =             Final | NonFinal | NativeDeclFinal,

        Readable =              0x00000100,
        Writable =              0x00000200,

        CodeInjection =         0x00001000,
        Rename =                0x00002000,
        Deprecated =            0x00004000,
        ReplaceExpression =     0x00008000,
        VirtualSlot =           0x00010000 | NonFinal,
        AllowAsSlot =           0x00020000,
        PrivateSignal =         0x00040000
    };

    Modification() : modifiers(0) { }

    bool isAccessModifier() const { return modifiers & AccessModifierMask; }
    Modifiers accessModifier() const { return Modifiers(modifiers & AccessModifierMask); }
    bool isPrivate() const { return accessModifier() == Private; }
    bool isProtected() const { return accessModifier() == Protected; }
    bool isPublic() const { return accessModifier() == Public; }
    bool isFriendly() const { return accessModifier() == Friendly; }
    bool isFinal() const { return modifiers & Final; }
    bool isDeclaredFinal() const { return modifiers & NativeDeclFinal; }
    bool isNonFinal() const { return (modifiers & NonFinal) && !(modifiers & NativeDeclFinal); }
    bool isVirtualSlot() const { return (modifiers & VirtualSlot) == VirtualSlot; }
    bool isAllowedAsSlot() const { return (modifiers & AllowAsSlot) == AllowAsSlot; }
    bool isPrivateSignal() const { return (modifiers & PrivateSignal) == PrivateSignal; }
    QString accessModifierString() const;

    bool isDeprecated() const { return modifiers & Deprecated; }

    void setRenamedTo(const QString &name) { renamedToName = name; }
    QString renamedTo() const { return renamedToName; }
    bool isRenameModifier() const { return modifiers & Rename; }

    uint modifiers;
    QString renamedToName;
};

struct TemplateInstantiation: public Modification {
    QList<QString> arguments;
};

typedef QList<TemplateInstantiation> TemplateInstantiationList;

struct FunctionModification: public Modification {
    FunctionModification() : removal(TypeSystem::NoLanguage) { }

    bool isCodeInjection() const { return modifiers & CodeInjection; }
    bool isRemoveModifier() const { return removal != TypeSystem::NoLanguage; }

    QString toString() const;

    QString signature;
    QString association;
    CodeSnipList snips;
    TypeSystem::Language removal;

    QList<ArgumentModification> argument_mods;
    QList<TemplateInstantiation> template_instantiations;
};
typedef QList<FunctionModification> FunctionModificationList;

struct FieldModification: public Modification {
    bool isReadable() const { return modifiers & Readable; }
    bool isWritable() const { return modifiers & Writable; }

    QString name;
};
typedef QList<FieldModification> FieldModificationList;

#endif
