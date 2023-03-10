package rodrigues.ferreira.ricardo.website.personalwebsite.entity;

public enum StatusPost {

    PUBLISHED("published"),
    UNPUBLISHED("unpublished"),
    DRAFT("draft");

    private String description;

    StatusPost(String published) {
        this.description = published;
    }

    public String getDescription() {
        return this.description;
    }

}
