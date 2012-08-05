簡介
----
在 ZK 6.1 EE 版中，我們設計了一個新的元件 Dropupload。這個元件使用 HTML5 的技術，讓使用者可以透過拖曳的方式指定要上傳的檔案，並保有原本檔案上傳的功能與操作習慣。另外，相較於傳統的 File Upload 方式，Dropupload 也可以省去 pooling server 以取得 progress 的 overhead，大幅降低 server 負荷。

Live Demo
---------

新屬性：detection
----------------
因應 Drag and Drop 的行為，我們增加了 `detection` 這個屬性，讓你可以設定當使用者將檔案 drag 到哪裡的時候會顯示 Dropupload 以及提示資訊。`detection` 接受下列四種值：
* `none` : 無視 DnD 的動作，永遠顯示 Dropupload 與提示資訊。
* `browser` (default) : 正常狀況下並不會顯示。當使用者將檔案拖曳進 browser 時就會顯示 Dropupload 以及提示資訊。
* `self` : 正常狀況下會顯示 Dropupload 但不會顯示提示資訊，當使用者將檔案拖曳進 Dropupload 元件的範圍時顯示提示資訊。
* 其他元件的 id : 與 self 相同，但觸發的範圍是指定 id 的元件。

轉換原有的 File Viewer
---------------------
就跟傳統的 File Upload 一樣，在 Dropupload 可以設定 viewerClass 屬性來使用指定的 File Viewer。而過去寫好的客製化 File Viewer 也可以繼續沿用，只需要作一點小改變：
* $init() 的第二個參數從原本的 filenm（String）改變成 file（[File](http://www.w3.org/TR/FileAPI/)）物件。增加 `var filenam = file.name` 就解決了。此外還可以透過 `file.size` 取得檔案大小（Bytes）、`file.type` 取得檔案的 MIME 資訊。
* update() 的第一個參數 send，原本會傳入一個介於 0～100 的整數值，代表已經上傳的百分比；現在則是改為傳入**已上傳的資料量（Bytes）**
就這麼多了，是不是很簡單呢？

Browser 支援度？
---------------
是的，由於 Dropupload 使用 HTML5 的技術，所以有些 browser 無法支援的。目前在 Firefox (v.13)、Chrome (v.19)、Safari (v.5.1.x) 確定是可以正常運作，而在 IE9、Opera v.11.x 無法使用這個功能。

另外，在一些**有點年紀**的機器上，detection 的設定會無法顯示。

reference
---------
* [ZK 5: New File Upload](http://books.zkoss.org/wiki/Small_Talks/2009/July/ZK_5:_New_File_Upload)