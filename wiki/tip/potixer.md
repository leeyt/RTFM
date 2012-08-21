> 這是針對__開發 ZK 的人__的相關 tip。  
> （aka: 正常人不需要知道這些 XD）

Tablet
======
* 複寫 js：`/src/archive/web/js/zk.wpd` 加上對應的 `<script>` 內容。
* 複寫 css.dsp:`/src/archive/web/zkmax/css/tablet.css.dsp`
	* 一兩行就解決可以直接塞在 `tablet.css.dsp` 裡頭
	* 反之則是加 `<c:include page="tablet/foo.css.dsp"/>`，然後在 `foo.css.dsp` 裡頭解決

在 `widget-touch.js` 把 `widget` 的 `setMold()` 覆寫成 `zk.$void`，
所以 component 必須要再覆寫一次 `setMold()` 才能讓 developer 設定 mold。