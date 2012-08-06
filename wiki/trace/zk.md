widget.js
=========
6.5.0

flex 機制
---------
> vflex 跟 hflex 的 setter，邏輯不盡相同？ 一個是
>
>		if (this.desktop) //$define[vflex]
> 另一個是
>
> 		if (_binds[this.uuid] == this)  //setHflex_
> 該不會 `this.desktop` 跟 `_binds[this.uuid]==this` 其實等意吧？ [死]
>
> `_fixMinVflex()` 是設定 `hflex` 的、`_fixMinHflex()` 是設定 vflex 的，這不是很怪嗎？

`setWidth()` 會先判斷 `!this._nhflex`，也就是說沒有設定 hflex 才會讓他設定 width，
`setHeight()` 邏輯雷同。

### `hflex="min"` 流程 ###
*	this._nhflex = -65500
*	註冊 listen
	*	在 `_listenFlex()`，onSzie : zFlex.onSize
	*	在 `_listenFlex()`，beforeSize : zFlex.beforeSize
	*	在 `widget.listenOnFitSize()`，onFitSize : zFlex.onFitSize

以實驗結果來看，會先執行
1.	beforeSize
1.	onFitSize→_fixMinHflex()→_fixMinVflex()
	* 理論上跑完這個 method，大小會調整好
1.	onSize→fixFlex
	* 這個又臭又長的就不知道是在幹啥了......

`this._nhflex` 的值取決於 `hflex` 的設定值（`setHFlex_`）：
*	true:1
*	min：-65500
*	負數：0

除非 hflex 設定為負數（先不考慮），不然會作 `_listenFlex(this)`。
`_listenFlex()` 只會作一次，主要是註冊 onSize（zFlex.onSize）、beforeSize（zFlex.beforeSize），
如果設定為 min 則跑 listenOnFitSize_()、反之則跑 unlistenOnFitSize_()。