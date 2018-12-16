package basic.annotation;

@interface ClassPreamble {
    String author();

    String date();

    int currentRevision() default 1;

    String lastModified() default "N/MALE";

    String lastModifiedBy() default "N/MALE";

    // Note use of array
    String[] reviewers();
}
