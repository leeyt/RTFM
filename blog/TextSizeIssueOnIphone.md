> # Iphone 遇到文字突然變大的問題 #

這是在作 [ZKForumViewer] 遇到的問題。精簡後程式碼如下

	<zk>
	<style>
	.autoSkip {
		display: block;
		overflow: hidden;
		text-overflow: ellipsis;  
		white-space: nowrap;
	}
	</style>
	<hlayout>
		<div hflex="1">
			<label value="author1"/>
			<label value='post1' />
			<html sclass="autoSkip" >
				<attribute name="content"><![CDATA[
				How to implement a moving chart
				]]></attribute>
			</html>
		</div>
	</hlayout>
	<separator />
	<hlayout>
		<div hflex="1">
			<label value="author2"/>
			<label value='post2' />
			<html sclass="autoSkip" >
				<attribute name="content"><![CDATA[
				How to implement a moving chart (like in task manager CPU usage history) using only ZK charts.
				]]></attribute>
			</html>
		</div>
	</hlayout>
	<separator />
	<hlayout>
		<div hflex="1">
			<label value="author3"/>
			<label value='post3' />
			<label sclass="autoSkip">
				<attribute name="value"><![CDATA[
				How to implement a moving chart (like in task manager CPU usage history) using only ZK charts.
				]]></attribute>
			</label>
		</div>
	</hlayout>
	</zk>

在 desktop 的環境下，大致上所有瀏覽器都沒有問題；mobile 上頭 iPad/Android（某台 ASUS）也沒有問題，
但是在 iPhone 上就會炸掉：

* portrait：author1 正常，author2、author3、post2、post3 變大。
* landscape：除了 post3 的內容之外，其餘全部變大。
	* 轉回 portrait：一樣不正常...... Orz

要<strike>招換神龍</strike>製造出這個現象，則下面條件全部都要備齊：

* 用 `hlayout` 包起來
* 一定要有兩個以上的 `label`
* 之後的字串掛上 `autoSkip` 這個 style（主要是將過長的內容會截斷變成「...」）
	* 有產生「...」的內容才會觸發這個狀況
	* 如果拿掉 `autoskip` 當中的 `white-space: nowrap;` 就沒有問題（當然也沒啥意義就是了）
	* 本來以為是 ZK 的 `.z-hlayout-inner` 有下 `white-space: normal;` 造成的問題（很無知的猜測 XD），
	  不過拿掉結果也一樣。

還有其他各種排列組合的詭異現象（landscape 跟 portrait 的結果不一樣就夠嚇人了），
就不另外一一列出來了（坦白說，我也忘了...... [毆飛]）。

雖然沒有以純 html 複製一次結果，不過看起來應該是 iPhone browser 的 rendering engine 有問題，
而不是 ZK（6.5） or CSS 寫法的問題，畢竟其他 browser 都沒問題、而 iPhone 上的問題又幾乎毫無邏輯可言。

於是瀏覽器差異的歷史，又這樣增加了新的一頁...... \[核爆]
[ZKForumViewer]: https://github.com/MontyPan/ZKForumViewer