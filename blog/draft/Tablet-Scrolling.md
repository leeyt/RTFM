>	Layout Scrolling Issue on Tablet 

Instruction
===========
以網頁的視覺效果來說，Tablet 與 Desktop 最顯著的不同處
在於 Tablet 並不會出現 scroll bar、也沒有來自鍵盤或滑鼠的輸入訊號，
取而代之的是以上下的 swipe 動作來代替捲動的行為。
ZK component 在 6.5 版針對這個特性做了許多強化，
本篇文章以 tablet 上的 scrolling 為主軸，解釋其運作原理、使用方式，
最後用一個範例講解從 desktop 轉換到 tablet 的要點提示。

Principle
=========
![scroll diagram](image/scrollDiagram.png)

在 desktop 上會產生 scroll bar，是因為 container 的高度比 content 的高度小，
然後以這兩個數值去決定 scroll bar 的外觀與行為。

在 tablet 上，ZK 會自動去計算畫面上各個支援 scrolling 的 component 高度、描繪自製的 scroll bar、
並將 swipe 動作轉換成 scrolling 行為。

ZK Component Support
====================
單純看「是否能滾動」，tablet 與 desktop 上頭的運作邏輯是相同的。
如果 component 原本就能滾動、或是有支援滾動的功能，那麼在 tablet 上也有支援。
反之，__如果沒有支援，即使用 CSS hacking 等方式讓它能滾動，在 tablet 上也不保證能滾動__，
這點在將 project 轉換到 tablet 上時需要特別注意。

與 desktop 相同，下面這三個 component 預設是開啟處理 scrolling 行為：

* Grid
* Listbox
* Tree

如果不希望這些元件處理 scrolling 動作，則需要另外設定 `data-scrollable` 屬性，例如：

	<listbox xmlns:ca="client/attribute" ca:data-scrollable="false" />

與 desktop 相同，下面這些 component 有支援 scrolling 功能，但是需要設定相關屬性才會開啟：

* (Borderlayout) Center, Ease, West, North, South: autoScroll="true"
* Deteail(Grid), Groupbox, Window: contentStyle="overflow:auto"
* PanelChildren, Tabpanel: style="overflow:auto"

詳細細節請參閱《[ZK Component Reference](http://books.zkoss.org/wiki/ZK_Component_Reference)》。

另外 ZK 6.5 版提供 `ScrollView` 這個 container component，可以讓它的 content 變成可以滾動。
詳情請參閱 [ScrollView Document](http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/Components/Scrollview)

Other Issues
------------
如果 parent component 跟 child component 都支援 scrolling，
那麼 swipe 的動作會在 child component 處理掉，而 parent 不會產生 scrolling 動作。
像下面這段程式碼，使用者將無法看到 footer：

	<borderlayout height="100px">
		<center autoscroll="true">
			<div height="100px">
				<listbox height="100px">
					<listitem forEach="1,2,3,4,5">
						<listcell label="${each}"></listcell>
					</listitem>
				</listbox>
				<label>footer</label>
			</div>
		</center>
	</borderlayout>

另外，tablet browser 本身也有 scrolling 的行為（在 iOS 5 以前需要用兩隻手指頭才有滾動效果），
像下面這段程式碼，雖然 `<div>` 沒有 scrolling 的功能，
但仍然可以透過 browser 原生的 scrolling 行為，看到最下方的 footer。

	<zk>
		<div style="background-color:green" width="100%" height="2000px" />
		<label>footer</label>
	</zk>

Example
=======
講解完原理與 component 的支援度，接下來我們將實際改寫一個範例，讓 developer 更能掌握 scrolling 的運作方式，
且能輕鬆地轉換既有的程式碼、使其能在 Tablet 上正常運作。

Wrong layout but can work
-------------------------
首先看一下這段程式碼：

	<window id="root">
		<tabbox width="100%">
			<tabs>
				<tab label="Demo"/>
			</tabs>
			<tabpanels><tabpanel>
				<window id="inner">
					<listbox id="lbx" />
					<zscript><![CDATA[
					//generate data for listbox
					String[] data = new String[50];
					for (int i = 0; i < data.length; i++) {
						data[i] = "item " + i;
					}
					org.zkoss.zul.ListModel strset = new org.zkoss.zul.SimpleListModel(data);
					lbx.setModel(strset);
					]]></zscript>
				</window>
			</tabpanel></tabpanels>
		</tabbox>
	</window>
	
看起來似乎沒什麼問題、在 desktop 上會出現 scrolling bar、畫面捲動也正常，
可是在 tablet 上卻不能滾動，這是為什麼呢？

其實這個例子根本沒有用到 `Listbox` 的 scrolling 功能，
因為沒有對 Listbox 的高度作任何限制，所以 `Listbox` 撐滿了 50 個 item 的高度。
在 desktop 上頭看到的 scrolling bar 是 bowser 對__整個頁面__產生的，
所以到了 tablet 上，`Listbox` 會沒辦法滾動。

如果 `Listbox` 設定 `width="50%"` 會更清楚呈現出這個問題：左半邊 `Listbox` 是無法滾動的，而如果對右半邊空白處作 swipe 動作，則__整個頁面__ 會跟著滾動，也就可以看到 `Listbox` 的其他資料。

那麼，原來的程式碼要怎麼修改才能在 tablet 上頭運作正常呢？
當然，最簡單的方式是將 `Listbox` 設定一個固定高度；
但是如果要作到 flexible 的 layout 要怎麼作呢？

Solution
--------
首先，得讓 `Listbox` 的 parent 們都有明確的高度限制，
所以兩個 `Window` 與 `Tabbox` 都加上 `height="100%"` 的屬性。
然後對 `Listbox` 設定 `vflex="1"` 的屬性，讓 ZK 去計算 `Listbox` 最後的高度。
最後的結果會是這樣：

	<window id="root" height="100%">
		<tabbox width="100%" height="100%">
			<tabs>
				<tab label="Demo"/>
			</tabs>
			<tabpanels><tabpanel>
				<window id="inner" height="100%">
					<listbox id="lbx" vflex="1" />
					<zscript><![CDATA[
					String[] data = new String[50];
					for (int i = 0; i < data.length; i++) {
						data[i] = "item " + i;
					}
					org.zkoss.zul.ListModel strset = new org.zkoss.zul.SimpleListModel(data);
					lbx.setModel(strset);
					]]></zscript>
				</window>
			</tabpanel></tabpanels>
		</tabbox>
	</window>

另外一種視覺上等價的方式，是讓 `inner` 這個 `Window` 負責滾動，
如此一來，`Listbox` 就無須設定 `vflex`，必須讓它有完整的高度，
然後設定為不能滾動，讓底層的 Window 可以接收到 scrolling 的 event。
程式碼如下：

	<window id="root" height="100%">
		<tabbox width="100%" height="100%">
			<tabs>
				<tab label="Demo"/>
			</tabs>
			<tabpanels><tabpanel>
				<window id="inner" height="100%" contentStyle="overflow:auto">
					<listbox id="lbx" xmlns:ca="client/attribute" ca:data-scrollable="false" />
					<zscript><![CDATA[
					String[] data = new String[50];
					for (int i = 0; i < data.length; i++) {
						data[i] = "item " + i;
					}
					org.zkoss.zul.ListModel strset = new org.zkoss.zul.SimpleListModel(data);
					lbx.setModel(strset);
					]]></zscript>
				</window>
			</tabpanel></tabpanels>
		</tabbox>
	</window>

Conclusion
==========
平心而論，scrolling 的機制在 desktop 與 tablet 上並沒有那麼大的差異。
而是過去在 desktop 上即使寫出不太正確的 layout，也不會產生嚴重的操作障礙，
所以在轉換到 tablet 時會將這些誤用之處給凸顯出來。
如果我們在撰寫 tablet 時有注意到本篇文章所提示的重點，
相信可以大幅降低從 desktop 程式轉換到 tablet 的困擾與成本。

Reference
=========
* http://books.zkoss.org/wiki/ZK_Developer's_Reference/UI_Patterns/Hflex_and_Vflex
* http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices