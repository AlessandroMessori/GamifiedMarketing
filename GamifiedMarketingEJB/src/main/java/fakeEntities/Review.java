package fakeEntities;


public class Review {

    private final String uname;
    private final int starCount;
    private final String review;

    public Review(String uname, int starCount, String review) {
        this.uname = uname;
        this.starCount = starCount;
        this.review = review;
    }

    public String getUname() {
        return uname;
    }

    public int getStarCount() {
        return starCount;
    }

    public String getReview() {
        return review;
    }

}
