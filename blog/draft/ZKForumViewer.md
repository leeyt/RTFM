ZK 6.1 版專注的重點在於加強開發人員撰寫 tablet 版 web application 的流暢度，甚至無須特別為 tablet 撰寫另一個版本。

The focus of ZK 6.1 is improved efficiency of create tablet web application, developer even won't write another version.

PC vs Tablet
------------

Beside PC, there is no mouse on tablet, keyboard is simulated, and screen size is smaller. For these limitation, developer must change another thinking to solve UX issues such as design layout, input style and handle swipe action. ZK is already solved this question.

有別於 PC，tablet 的螢幕較小、沒有滑鼠、輸入得靠軟體模擬鍵盤。在這種先天限制下，製作 tablet 的 web application 時，勢必得換另一種思維來思考諸如 layout 設計、輸入方式、swipe 行為等諸多 User experience 議題。而 ZK 已經解決幫你解決這些問題了。

What ZK does in Tablet?
-----------------------

ZK 做了什麼？

To give consideration to end user UX and developer burden, the solution ZK provide is "Component has different style and behavior on PC and tablet separately." Take Colorbox for example, button, gradient block and circle bar are magnified, spacing between buttons are magnified too. Datebox change input sytle totally. It will not appear calendar but provide a wheel scroller that end user can spacify date easily. Listbox will hide scrolling bar and capture swipe event, such that end user can scroll and browse.

為了兼顧 end user 的 UX 以及開發人員的負荷，ZK 的作法是：「讓同一個元件在 PC 與 tablet 下，分別有不同的行為與樣式。」以 Colorbox 來說，color picker 模式下 gradient block 與 circle bar 都變大了，按鈕之間的間距也加大以避免誤按。Datebox 則是徹底改變輸入方式，不再出現月曆、而是以類似吃角子老虎的方式讓使用者可以輕鬆地指定日期。至於 Listbox，在 tablet 的環境下不會出現捲軸，但是會捕捉 swipe 動作讓 end user 可以上下捲動 item 並瀏覽。

We just point out some representative instance. Actually, most component are tuned carefully. If design suitably, developer can write once and run on both tablet and PC.

這裡只舉出幾個代表性的例子。事實上，絕大多數的 component 都精心調校過，只要設計得當，開發人員可以只寫一份程式碼，卻同時能讓使用 tablet 與 desktop browser 的 end user 瀏覽。

Really?
-------
真的嗎？

Yes, we create a demo project : ZKForumViewer. It use ZK core component completely, override next to nothing CSS, and just write a bit of CSS. How good the effect of demo project? Please watch this vedio :

是的，我們寫了一個 demo 的 project：ZKForumViewer，完全使用 ZK 原生的 component、幾乎沒有覆寫原本的 CSS、也只額外寫了一點點 CSS。效果如何呢？請看這個影片：

<vedio>

You can clone the source code and run on your environment. It's working, and perform well!

你也可以下載 source code，在自己的環境下執行看看。是可以運作的，而且運作良好！

Enjoy ZK 6.1 and have fun!