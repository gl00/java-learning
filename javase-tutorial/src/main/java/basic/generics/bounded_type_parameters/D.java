package basic.generics.bounded_type_parameters;

/*
类型参数可以有多个边界。
具有多个边界的类型变量是边界中列出的所有类型的子类型。如果其中一个边界是一个类，则必须先指定它。
注意：多个边界中最多只能有一个边界是类
 */
public class D<T extends A & B & C> {
}
