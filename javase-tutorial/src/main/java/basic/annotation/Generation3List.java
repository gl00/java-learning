package basic.annotation;

@ClassPreamble(
        author = "John Doe",
        date = "3/17/2002",
        currentRevision = 6,
        lastModified = "4/24/2004",
        lastModifiedBy = "Jane Doe",
        // Note array notation
        reviewers = {"Alice", "Bob", "Cindy"}
)
public class Generation3List extends Generation2List {
}
