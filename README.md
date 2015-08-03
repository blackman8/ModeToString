# ModeToString
用来打印子类(继承ModelToString)中声明的所有字段信息(声明了get或is为前缀方法的字段)。在子类的toString方法中调用父类的toString()方法。后续子类的toString将打印所有字段信息.如果没有字段信息；或者没有get,is前缀的方法时，会打印错误信息（parse error之类的信息）。
