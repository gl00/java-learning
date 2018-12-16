package basic.class_object.exercise;

public @interface RequestForEnhancement {
	int id();

	String synopsis();

	String engineer() default "unsigned";

	String date() default "unknown";
}
