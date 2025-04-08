package com.example.guru.form;

import java.util.List;

/**
 * News APIフォームクラス。
 * 
 * News APIから取得したニュースデータを格納するためのフォームクラス。
 * 
 * @version 1.0
 * @author kota
 * @since 2025-03-19
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

    /**
     * APIリクエストのステータスを取得します。
     * 
     * @return ステータス
     */
    public String getStatus() {
        return status;
    }

    /**
     * APIリクエストのステータスを設定します。
     * 
     * @param status ステータス
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 取得可能な記事の総数を取得します。
     * 
     * @return 記事の総数
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * 取得可能な記事の総数を設定します。
     * 
     * @param totalResults 記事の総数
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * ニュース記事のリストを取得します。
     * 
     * @return 記事リスト
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * ニュース記事のリストを設定します。
     * 
     * @param articles 記事リスト
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /**
     * ニュース記事の詳細情報を格納する内部クラス。
     * 各記事のソース、著者、タイトルなどの情報を保持します。
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

        /**
         * 記事のソースを取得します。
         * 
         * @return ソース情報
         */
        public Source getSource() {
            return source;
        }

        /**
         * 記事のソースを設定します。
         * 
         * @param source ソース情報
         */
        public void setSource(Source source) {
            this.source = source;
        }

        /**
         * 記事の著者を取得します。
         * 
         * @return 著者名
         */
        public String getAuthor() {
            return author;
        }

        /**
         * 記事の著者を設定します。
         * 
         * @param author 著者名
         */
        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * 記事のタイトルを取得します。
         * 
         * @return タイトル
         */
        public String getTitle() {
            return title;
        }

        /**
         * 記事のタイトルを設定します。
         * 
         * @param title タイトル
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * 記事の説明を取得します。
         * 
         * @return 説明
         */
        public String getDescription() {
            return description;
        }

        /**
         * 記事の説明を設定します。
         * 
         * @param description 説明
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * 記事のURLを取得します。
         * 
         * @return URL
         */
        public String getUrl() {
            return url;
        }

        /**
         * 記事のURLを設定します。
         * 
         * @param url URL
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * 記事の画像URLを取得します。
         * 
         * @return 画像URL
         */
        public String getUrlToImage() {
            return urlToImage;
        }

        /**
         * 記事の画像URLを設定します。
         * 
         * @param urlToImage 画像URL
         */
        public void setUrlToImage(String urlToImage) {
            this.urlToImage = urlToImage;
        }

        /**
         * 記事の公開日時を取得します。
         * 
         * @return 公開日時
         */
        public String getPublishedAt() {
            return publishedAt;
        }

        /**
         * 記事の公開日時を設定します。
         * 
         * @param publishedAt 公開日時
         */
        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        /**
         * 記事のコンテンツを取得します。
         * 
         * @return コンテンツ
         */
        public String getContent() {
            return content;
        }

        /**
         * 記事のコンテンツを設定します。
         * 
         * @param content コンテンツ
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * 記事のソース情報を格納する内部クラス。
         * ニュース提供元の詳細（IDと名前）を保持します。
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

            /**
             * ソースのIDを取得します。
             * 
             * @return ソースID
             */
            public String getId() {
                return id;
            }

            /**
             * ソースのIDを設定します。
             * 
             * @param id ソースID
             */
            public void setId(String id) {
                this.id = id;
            }

            /**
             * ソースの名前を取得します。
             * 
             * @return ソース名
             */
            public String getName() {
                return name;
            }

            /**
             * ソースの名前を設定します。
             * 
             * @param name ソース名
             */
            public void setName(String name) {
                this.name = name;
            }
        }
    }
}