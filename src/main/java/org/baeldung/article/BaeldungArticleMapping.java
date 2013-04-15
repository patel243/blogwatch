package org.baeldung.article;

public final class BaeldungArticleMapping {

    private BaeldungArticleMapping() {
        throw new AssertionError();
    }

    // API

    public static String mapToUrl(final String name) {
        switch (name) {
        case "Multipart Upload on S3 with jclouds":
            return "http://www.baeldung.com/2013/04/04/multipart-upload-on-s3-with-jclouds/";

        default:
            break;
        }

        return "";
    }

}
