> # Tablet Programming Tips #

Introduction
------------
ZK 6.5 推出之後，developer 可以用 ZK 開發出 tablet 能順暢瀏覽的網站。
但由於先天硬體設備的差異，許多設計上的思維與細節必須隨之作調整，才能兼顧 Desktop 與 Tablet，並提供良好的 UX。
這篇文章就以 ZK 6.5 為基礎，講解其中所需要掌控的細節。

Different Style
---------------
由於在 Tablet 上只能用手指頭來進行操作，所以像 Button 這類提供 end-user 點擊的 component 大小不能太小，24*24px 大概是最低底線。
反過來說，如果 component 較小，component 之間的間距就要加大，以防止 end-user 誤按到鄰近的 component。
ZK 也根據這樣的思維將相關 component 做了一系列的調整，讓 developer 可以直接拿來在 Tablet 上使用。
例如 `Combobox`、`Timebox`、`Colorbox`，詳情請參閱 [Component Reference/Tablet Devices/Ui Enhancements]。

[Component Reference/Tablet Devices/Ui Enhancements]: http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/UI_Enhancements

### Mold Unsupport ###
在 Desktop 上，許多 component 提供了不同 mold 來改變外觀，
但是在 Tablet 上因為操作行為不適用等原因，所以不再支援這些 mold，
詳細列表可參閱 [ZK Component Reference/Tablet Devices/Unsupported_Molds]。
總結來說：

* input 類型的 component：不再支援 `rounded` mold
* `Button` 不支援 `os` 與 `trendy` mold
* `Groupbox` 不支援 `default` mold，預設就是 `3d` mold
* `Splitter` 不支援 `os` mold
* `Tabbox` 不支援 `accordion-lite` mold

[ZK Component Reference/Tablet Devices/Unsupported_Molds]: http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/Unsupported_Molds

Different Event
---------------
### Mouse Event ###
因為 Tablet 上頭並沒有滑鼠與游標，所以跟滑鼠相關 event 也略有不同。

首先是沒有 `onMouseOver` 這個 event，所以像是 `tooltip`、`autodrop` 的功能也都無法運作。
而滑鼠右鍵 `onRightClick` 則是 ZK client engine 用「按住不放」的行為來模擬，
所以在 Tablet 上 `context`（顯示 context menu）、`onRightClick` 都是可以正常運作的。

### ClientInfoEvent ###
Tablet 可以旋轉螢幕，而有 portrait 與 landscape 的區別。因此 ZK 6.5 在 ClientInfoEvent 中增加 orientation 的資訊，
developer 可以用 `onClientInfo` 取得 `ClientInfoEvent` 並透過 `getOrientation()` 
或是 `isPortrait()`、`isLandscape()` 來取得 orientation 資訊。例如：

	<tabbox id="tbx" height="400px" width="600px">
		<attribute name="onClientInfo"><![CDATA[
		ClientInfoEvent oe = (ClientInfoEvent) event;
		lbl.setValue(oe.getOrientation());
		if (oe.isPortrait()) {
			tbx.setHeight("600px");
			tbx.setWidth("400px");
		} else {
			tbx.setHeight("400px");
			tbx.setWidth("600px");
		}
		]]></attribute>
		<tabs>
			<tab label="tab 1" />
		</tabs>
		<tabpanels>
			<tabpanel>
				Current Orientation:
				<label id="lbl" />
			</tabpanel>
		</tabpanels>
	</tabbox>
	
Other Scrolling Issue
---------------------
Tablet 上的 scrolling 原理，請參閱 [Scrolling on Tablet]，這裡討論的是實做上需要注意的事項。

用 `<textbox multiline="true" />` 可以做出 text area，當內容過長時 text area 也可以 scrolling。
這邊必須特別注意的是，這個 scrolling 行為並不是由 ZK 生成與掌控的，而是 browser 自行處理的；
因此它的滾動行為會與其他 ZK component 上的不相同，

如果在系統中有用到 `Image`，必須給定大小或是啟用 preload，否則 ZK 在計算 scroll bar 時會無法取得正確大小。
例如：

	<zk>
		<window contentStyle="overflow:auto" height="300px" border="normal">
			<image src="http://www.zkoss.org/resource/img/index/src/top_bannerimage3.png" />
			<div>bottom</div>
		</window>
	</zk>

`window` 完全不會產生 scroll bar、也就沒辦法看到下方的 bottom 字樣。
解決方法是啟用 preload，例如：

	<zk>
		<window contentStyle="overflow:auto" height="300px" border="normal">
 			<custom-attributes org.zkoss.zul.image.preload="true"/>
			<image src="http://www.zkoss.org/resource/img/index/src/top_bannerimage3.png" />
			<div>bottom</div>
		</window>
	</zk>
	
或是指定高度

	<zk>
		<window contentStyle="overflow:auto" height="300px" border="normal">
			<image height="500px" 
			 src="http://www.zkoss.org/resource/img/index/src/top_bannerimage3.png" />
			<div>bottom</div>
		</window>
	</zk>
	
如此一來，就可以正確地 scroll。建議在 `zk.xml` 將 preload 預設開啟。

另外，不建議在 `Listbox` 上設定 `rows`，例如像下面這個例子：

	<zk>
		<zscript> String[] s = new String[100];	</zscript>
		<listbox id="lbx1" height="300px">
			<zk forEach="${s}">
				<listitem label="${forEachStatus.index + 1}" />
			</zk>
		</listbox>
		<listbox id="lbx2" rows="8">
			<zk forEach="${s}">
				<listitem label="${forEachStatus.index + 1}" />
			</zk>
		</listbox>		
	</zk>

在 iPad 上會發現 lbx1 的第十個 item 被截去部份，end-user 很容易就會察覺這個 item 的不完整、進而去滾動它；
而 lbx2 則沒有這樣的提示效果。這在 `<tree>` 與 `<grid>` 上也適用。

[Scrolling on Tablet]: Tablet-Scrolling.md

As Simple as Possible
---------------------
Tablet（甚至 Smart Phone）的螢幕較小，如前面所述，有些 component 為了提供良好的 UX 而變得較大，
因此並沒有辦法在 Tablet 上提供複雜的 layout、也無法要求 end-user 作細膩的操作。

Tablet 的運算能力也遠不如 Desktop，在 Desktop 上可能短到無法察覺的 rendering 時間，
在 Tablet 上則會放大到 end-user 可以察覺的程度。因此保持「Simple is beautiful」的設計理念是重要的，
另外將 `Listbox`、`Grid` 這些 component 的 ROD 功能開啟也可以有效提昇 UX。