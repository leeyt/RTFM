ZK 6.5 版專注的重點在於加強開發人員撰寫 tablet 版 web application 的流暢度，甚至無須特別為 tablet 撰寫另一個版本。

Desktop vs Tablet
-----------------
有別於 PC，tablet 的螢幕較小、沒有滑鼠、輸入得靠軟體模擬鍵盤。
在這種先天限制下，製作 tablet 的 web application 時，
勢必得換另一種思維來思考諸如 layout 設計、輸入方式、swipe 行為等諸多 User experience 議題。

ZK 6.5 版已經解決幫你解決這些問題了。

ZK 做了什麼？
-------------
為了兼顧 end user 的 UX 以及開發人員的負荷，
ZK 的作法是：「*讓同一個元件在 PC 與 tablet 下，分別有不同的行為與樣式。* 」
以 `Colorbox` 來說，color picker 模式下 gradient block 與 circle bar 都變大了，
按鈕之間的間距也加大以避免誤按。
Datebox 則是徹底改變輸入方式，不再出現月曆、而是以類似吃角子老虎的方式讓使用者可以輕鬆地指定日期。
至於 `Listbox`，在 tablet 的環境下不會出現捲軸，但是會捕捉 swipe 動作讓 end user 可以上下捲動 item 並瀏覽。

這裡只舉出幾個代表性的例子。
事實上，絕大多數的 component 都精心調校過，只要設計得當，開發人員可以只寫一份程式碼，
卻同時能讓使用 tablet 與 desktop browser 的 end user 瀏覽。

真的嗎？
-------
是的，我們寫了一個 demo 的 project：ZKForumViewer，
完全使用 ZK 原生的 component、幾乎沒有覆寫原本的 CSS、也只額外寫了一點點 CSS。效果如何呢？請看這個影片：

<vedio>

你也可以下載 source code，在自己的環境下執行看看。是可以運作的，而且運作良好！

Enjoy ZK 6.5 and have fun!