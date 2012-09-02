> # Cardlayout：用 Swipe 來切換 view #

簡介
======
我們在 ZK 6.5 版提供了一個新的 component *Cardlayout* ，讓使用者可以像操作卡片一樣在 component 之間切換。
在 Tablet 上還支援用 swipe 動作來改變畫面。

Live Demo
=========
<video>

Implement
=========
使用上就像 `HBox`、`Hlyout` 一樣，Developer 可以將任何 Component 加到 Cardlayout 下。
`selectedIndex` 會決定當下要顯示哪一個 component、`previous()` 與 `next()` 會改變 `selectedIndex` 的值然後切換畫面。

	<cardlayout id="card" width="300px" height="300px">
		<window title="Window Component" border="normal" width="200px">width: 200px</window>
		<window title="Window Component" border="normal" width="300px">width: 300px</window>
		<window title="Window Component" border="normal" width="400px">width: 400px</window>
	</cardlayout>
	<button onClick="card.previous()">previous</button>
	<button onClick="card.next()">next</button>

這裡有一個跟 size 有關的議題：`Cardlayout` 的大小是固定的，所以第三個 component 右邊 100px 的部份會看不到。

至於用 swipe 動作切換畫面的功能，你無須作任何設定，在 tablet 的環境下會自動運作。