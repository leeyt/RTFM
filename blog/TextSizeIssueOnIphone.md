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
				<label value="hflex=1"/>
				<label value='normal size' />
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
				<label value="hflex=1"/>
				<label value='wrong size' />
				<html sclass="autoSkip" >
					<attribute name="content"><![CDATA[
					How to implement a moving chart (like in task manager CPU usage history) using only ZK charts.
					]]></attribute>
				</html>
			</div>
		</hlayout>
		<separator />
		<hlayout>
			<div width="200px">
				<label value="width=200px"/>
				<label value='normal size' />
				<html sclass="autoSkip" >
					<attribute name="content"><![CDATA[
					How to implement a moving chart (like in task manager CPU usage history) using only ZK charts.
					]]></attribute>
				</html>
			</div>
		</hlayout>
	</zk>

在 desktop 的環境下，大致上所有瀏覽器都沒有問題；mobile 上頭 iPad/Android（某台 ASUS）也沒有問題，
但是在 iPhone 上就會炸掉：

* portrait：`hflex=1 normal size` 正常，`hflex=1 wrong size` 變大。
* landscape：`hflex=1 normal size` 跟 `hflex=1 wrong size` 都變大。
	* 轉回 portrait：一樣不正常...... Orz

要<strike>招換神龍</strike>製造出這個現象，則下面條件全部都要備齊：

* 用 `hlayout` 包起來
* `hlayout` 下的 `div` 設定 `vflex="1"`
	* 不設定沒有「...」的效果
	* __設定 width="200px" 則完全正常__
* 一定要有兩個以上的 `label`
* 之後的字串掛上 `autoSkip` 這個 style（主要是將過長的內容會截斷變成「...」）
	* 有產生「...」的內容才會觸發這個狀況
	* __如果拿掉 `autoskip` 當中的 `white-space: nowrap;` 就沒有問題（當然也沒啥意義就是了）__
	* __override `.z-hlayout.inner`，拿掉 `display: inline-block;` 也會沒事（當然也沒啥意義就是了）__

還有其他各種排列組合的詭異現象（landscape 跟 portrait 的結果不一樣就夠嚇人了），
就不另外一一列出來了（坦白說，我也忘了...... [毆飛]）。

在不斷地重複重複再～重複地實驗之後，最後成功地以純 html 複製出結果，程式碼如下：

	<html>
	<head>
		<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;">
		<style>
		.autoSkip {
			display: block;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
		</style>
	</head>
	<body>
		<div>
			<span class="autoSkip"> How to implement a moving chart
				(like in task manager CPU usage history) using only ZK charts. </span>
		</div>
		<hr />
		<div style="display: inline-block; width: 200px">
			<span class="autoSkip"> How to implement a moving chart
				(like in task manager CPU usage history) using only ZK charts. </span>
		</div>
		<hr />
		<div style="display: inline-block;">
			<span class="autoSkip"> How to implement a moving chart
				(like in task manager CPU usage history) using only ZK charts. </span>
		</div>
		<hr />
		<div style="display: inline-block;">
			<span id="foo" class="autoSkip"> How to implement a moving chart
				(like in task manager CPU usage history) using only ZK charts. </span>
		</div>
	</body>
	</html>
	<script>
	function foo(){
		//alert(document.getElementById("iGuP8").style.width);
		document.getElementById("foo").style.width="100px";
	}
	setTimeout(foo, 500);
	</script>

四個 case 差異分別如下（沒有包含「拿掉 `white-space: nowrap`」的 case）：

1. 正確：`div` 沒有給 `display: inline-block`，`span` 掛 `autoSkip`。
1. 正確：`div` 給 `display: inline-block` 並設定寬度、`span` 掛 `autoSkip`。
1. 錯誤：`div` 給 `display: inline-block`，`span` 掛 `autoSkip`
1. 錯誤：同上一個，只是透過 JS 設定寬度來模擬 ZK 中的 hflex 效果。

結論大概就是 iPhone 上頭的 parent element 的 `display: inline-block` 
加上 child element 的 `white-space: nowrap` 會導致 render 失常的現象。

於是瀏覽器差異的歷史，又這樣增加了新的一頁...... \[核爆]

[ZKForumViewer]: https://github.com/MontyPan/ZKForumViewer