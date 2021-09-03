java-log4j2-sample
==================
log4j2のサンプルソースコードです

Version
-------

* log4j2 2.3
* Java jdk_1.7.0_40
* Eclipse Kepler 4.3

Abstract
--------

* log4j2のライブラリを使ったソースコードです
* mainメソッドを持つサンプルクラスが1つあります
  * src/com/kakakikikeke/sample/Log4j2Sample.java

How to use
----------

### インストール方法

1. GithubからEclipseにプロジェクトを[インポート](http://kakakikikeke.blogspot.jp/2012/12/githubandroideclipse.html)します
1. プロジェクトのJavaのバージョンをビルドパスの構成から変更します

### 実行方法

* mainメソッドを実行する
  * src/com/kakakikikeke/sample/Log4j2Sample.java を実行します
  * サンプル用のmainメソッドが準備されていますので、Eclipse からであれば、クラスを右クリックし Run As -> Java Application で実行できます


Configure
---------

* log4j2.xmlの編集
  * src/main/java 配下のlog4j.xmlを編集できます
    * Eclipse + Maven では src/main/java 直下はデフォルトでクラスパスが通っています

Tips
----

* log4j2.xml と log4j2.json の2つプロパティファイルでロギング内容を操作できます
* log4j.properties はなくってみたいです、またlog4j→log4j2とファイル名の規則も変わっています
* コンソールにロギングするだけならプロパティファイルは必要です、その場合ログレベルがERRORで表示されます
* TRACE < DEBUG < INFO < WARN < ERROR < FATAL の順でログレベルは設定できます（デフォルトはERRORとFATALのみ表示されます）
  * http://logging.apache.org/log4j/2.x/manual/architecture.html
* 指定できる出力フォーマット
  * http://logging.apache.org/log4j/2.x/manual/layouts.html
* 出力に必要な最低限のタグ
    * appenders
      * コンソールに出力するフォーマットとファイルに出力するフォーマットを定義します
      * 指定できるappenders一覧
          * http://logging.apache.org/log4j/2.x/manual/appenders.html
      * RollingFileAppender
          * http://logging.apache.org/log4j/2.x/manual/appenders.html#RollingFileAppender
    * loggers
        * カテゴリを定義します
        * カテゴリはどのappendersを使うかを指定することでログの出力方式を決定します