> # Tablet Programming Tips #

Introduction
------------
ZK 6.5 推出之後，developer 可以用 ZK 開發出 tablet 能順暢瀏覽的網站。
但由於先天硬體設備的差異，許多設計上的細節並不如

這篇文章就以 ZK 6.5 為基礎，講解其中所需要掌控的細節。

Different Style
---------------

### Mold Unsupport ###
在 Desktop 上，許多 component 提供了不同 mold 來改變外觀，
但是在 Tablet 上因為操作行為不適用等原因，所以不再支援這些 mold，
詳細列表可參閱 [ZK Component Reference]。總結來說：

* input 類型的 component：不再支援 `rounded` mold
* `Button` 不支援 `os` 與 `trendy` mold
* `Groupbox` 不支援 `default` mold，預設就是 `3d` mold
* `Splitter` 不支援 `os`
* `Tabbox` 不支援 `accordion-lite`

[ZK Component Reference]: http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/Unsupported_Molds

Different Operation
-------------------
### Mouse Event ###
* no onMouseOver
	* can't provide tooltip.
	* no `autodrop` for some component
* right click => touch on the component for one second
	* focus issue
* onMouseMove will occur scrolling in some case.
* no upload, flash, 

Orientation change
------------------
* use `onClientInfo`, get ClientInfoEvent and call `getOrientation()` or `isVertical()`

Other Scrolling Issue
---------------------
* basic principle in [Scrolling on Tablet](Tablet-Scrolling.md)
* If parent component and it's children are scrollable, choose only one enable scrolling.
	* Scroll bar of `TextArea`（with `multiline="true"`） is not provided by ZK, 
	  so suggest disable it's parent's scrolling 
* Enable `<custom-attributes org.zkoss.zul.image.preload="true"/>`
	* `<image>` without height/width will occur scroll bar wrong.
* Does not suggest use `rows` in `<listbox>`, `<tree>` and `<grid>`,
  end-user can't identify it can scroll or not.
* 當 Listbox/Grid/Tree 內容太多時，要考慮讓他來負責滾動、而不是由 parent 來滾動
	* rod 與 iscroll 效率問題

[Scrolling on Tablet]: Tablet-Scrolling.md

As Simple as Possible
---------------------
* limit by screen size and operation
	* some component(like `Button` is bigger for better UX)
* render speed issue