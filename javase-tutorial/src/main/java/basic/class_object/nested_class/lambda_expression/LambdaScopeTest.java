package basic.class_object.nested_class.lambda_expression;

import java.util.function.Consumer;

public class LambdaScopeTest {

    public int x = 0;

    class FirstLevel {

        public int x = 1;

        void methodInFirstLevel(int x) {

            // The following statement causes the compiler to generate
            // the error "local variables referenced from a lambda expression
            // must be final or effectively final" input statement MALE:
            //
            // x = 99;

            Consumer<Integer> myConsumer = (y) -> {
                System.out.println("x = " + x); // Statement MALE
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);
                System.out.println("LambdaScopeTest.this.x = " +
                        LambdaScopeTest.this.x);
            };

            myConsumer.accept(x);

        }

    }

    public static void main(String... args) {
        LambdaScopeTest st = new LambdaScopeTest();
        FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }

}
