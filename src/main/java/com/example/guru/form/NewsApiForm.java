package com.example.guru.form;

import java.util.List;

/**
 * News APIから取得したニュースデータを格納するためのフォームクラス。
 * APIのレスポンス全体をマッピングし、ステータスや記事リストを保持する。
 */
public class NewsApiForm {

    /**
     * APIリクエストのステータス。
     * 例: "ok"（成功）または "error"（失敗）
     */
    private String status;

    /**
     * 取得可能な記事の総数。
     * 検索条件に一致する記事の全体数を表す。
     */
    private int totalResults;

    /**
     * ニュース記事のリスト。
     * 各記事には詳細情報（タイトル、著者、URLなど）が含まれる。
     */
    private List<Article> articles;

    // ゲッターとセッター
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /**
     * ニュース記事の詳細情報を格納する内部クラス。
     * 各記事のソース、著者、タイトルなどの情報を保持。
     */
    public static class Article {

        /**
         * 記事のソース（ニュース提供元）。
         * ソースのIDと名前を含む。
         */
        private Source source;

        /**
         * 記事の著者。
         * 著者名が不明な場合は null になる可能性あり。
         */
        private String author;

        /**
         * 記事のタイトル。
         * ニュースの見出しを表す。
         */
        private String title;

        /**
         * 記事の説明。
         * 記事の内容を簡潔にまとめたもの。
         */
        private String description;

        /**
         * 記事のURL。
         * 記事の全文が閲覧可能なリンク。
         */
        private String url;

        /**
         * 記事の画像URL。
         * 記事に関連する画像へのリンク（存在しない場合もあり）。
         */
        private String urlToImage;

        /**
         * 記事の公開日時。
         * ISO 8601形式（例: "2023-10-01T12:00:00Z"）で表される。
         */
        private String publishedAt;

        /**
         * 記事のコンテンツ。
         * 記事本文の一部（通常は冒頭部分のみ）が含まれる。
         */
        private String content;

        // ゲッターとセッター
        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        /**
         * 記事のソース情報を格納する内部クラス。
         * ニュース提供元の詳細（IDと名前）を保持。
         */
        public static class Source {

            /**
             * ソースのID。
             * 例: "techcrunch"（一意の識別子、nullの場合もあり）。
             */
            private String id;

            /**
             * ソースの名前。
             * 例: "TechCrunch"（ニュース提供元の表示名）。
             */
            private String name;

            // ゲッターとセッター
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}