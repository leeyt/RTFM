> 這是針對__開發 ZK 的人__的相關 tip。  
> （aka: 正常人不需要知道這些 XD）

JavaScript
==========
ZK
--
### zk.Object 相關 ###
	foo = zk.$extends(zk.Object, {
		//instance method/field
	},{
		//static method/field
		fooValue: 0;
	}

在 `foo` 當中可以用 `this.$class.fooValue` 存取

### Event 相關 ###
`new zk.Event(widget, name, data, opt)`：`data` 是回傳到 server side 的（不過 client 還是取得到 XD）

jsdoc
-----
步驟：
1. `./build jsdoc zk zul ../zkcml/zkex ../zkcml/zkmax;`
	* 會把相關的 .js 轉換成（假的） .java 檔
1. `./build doc jsdoc;`
	* 透過上一個步驟產生的（假的） .java 檔來吐出 javadoc

### 除錯 tip ###
* class 前頭（`foo.FooName = zk.$extends(zk.Object, {}`）前頭一定要有 javadoc comment block，
  不然步驟 1 就不會產生對應的 .java 檔、步驟 2 就會炸找不到 `FooName` 的錯誤。
* `$define` 當中 setter 的 `@param` 一定要記得給 data type，
  不然會被當成 getter 然後炸重複宣告的 error
* `@return` 打成 `@returns` 居然 Eclipse 跟 `./build jsdoc` 都照吃，
   然後在步驟 2 會把多的那個 s 當成 return 值的 data type... （WTF）

swipe
-----
主要邏輯在 ZK 的 zswipe.js（當然 widget.js 也有）

widget 的 `_swipe` 通常在 `bindSwipe_()` 當中設定，`_swipe` 會決定 `doSwipe_(evnt)` 的 `evnt.opts.dir` 的值。
簡單地說，就是決定到底是上下左右哪一個（`zswipe.js` 的 `_swipeEnd()`），而不是決定 swipe event 發不發。
也就是說，無論 `_swipe` 怎麼設定，`doSwipe_()` 還是會呼叫到。

ZUL、EL
======
* `${zk}` 的來源（應該）是 `Servlets.java` 的 `browserInfo()` 
  偷偷塞 `request.setAttribute("zk", zk = new HashMap<String, Object>(4));` 的結果
  
Tablet
======
* 複寫 js：`/src/archive/web/js/zk.wpd` 加上對應的 `<script>` 內容。
* 複寫 css.dsp:`/src/archive/web/zkmax/css/tablet.css.dsp`
	* 一兩行就解決可以直接塞在 `tablet.css.dsp` 裡頭
	* 反之則是加 `<c:include page="tablet/foo.css.dsp"/>`，然後在 `foo.css.dsp` 裡頭解決

在 `widget-touch.js` 把 `widget` 的 `setMold()` 覆寫成 `zk.$void`，
所以 component 必須要再覆寫一次 `setMold()` 才能讓 developer 設定 mold。