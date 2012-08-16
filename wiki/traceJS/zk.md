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
`this._nhflex` 的值取決於 `hflex` 的設定值（`setHFlex_`）：

*	true:1
*	min：-65500
*	負數：0

接著作 `_listenFlex(this)`。`_listenFlex()` 只會作一次，
主要是註冊 onSize（zFlex.onSize）、beforeSize（zFlex.beforeSize），
因為 hflex="min" 會加跑 `listenOnFitSize_()`（反之則跑 `unlistenOnFitSize_()`）。

* this._nhflex = -65500
* 註冊 listen
	* 在 `_listenFlex()`，onSzie : zFlex.onSize
	* 在 `_listenFlex()`，beforeSize : zFlex.beforeSize
	* 在 `widget.listenOnFitSize()`，onFitSize : zFlex.onFitSize

以實驗結果來看，會先執行

1. beforeSize
1. onFitSize→_fixMinHflex()→_fixMinVflex()
	* 理論上跑完這個 method，大小會調整好
1. onSize→fixFlex

### $n() ###
與 `jq(subId)[0]` 等價，只是它會把回傳值存一份在 `this._subnodes[subId]` 當中。
cleanCache() 可以清空。

flex.js
=======
### _fixMinVflex() ###
如果有給 min
（也就是 `fixMinFlex()` 當中 `wgt.beforeMinFlex_()` 有回傳值、可是 `widget.beforeMinFlex_()` 並沒有寫任何東西，
也就是說是實際計算大小的 widget 必須覆寫這個 method），那麼中間那一大串（第二層 if-else）就會跳過，直接進入最後一段。

最後一段的重點在於 `wgt.setFlexSize_()`。
按照原本 widget.js 的寫法，真正影響 widget 寬度的，其實是 `widget.setFlexSize_()`。