> # Tablet Design Tip #

Different Operation
-------------------
* Different input style
	* Datebox
	* Combobox
	* Colorbox
* Different Mouse Event
* no onMouseOver
	* can't provide tooltip.
* right click => long push the screen
	* focus issue
* onMouseMove will occur scrolling in some case.

Orientation change
------------------
* use `onClientInfo`, get ClientInfoEvent and call `getOrientation()` or `isVertical()`

Mold Support
------------
* provide a table or a list like http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/Unsupported_Molds

Other Scrolling Issue
---------------------
* basic principle in [Scrolling on Tablet](Tablet-Scrolling.md)
* If parent component and it's children are scrollable, choose only one enable scrolling.
	* Scroll bar of `TextArea`（with `multiline="true"`） is not provided by ZK, 
	  so suggest disable it's parent's scrolling 
* Enable `<custom-attributes org.zkoss.zul.image.preload="true"/>`
	* `<image>` without height/width will occur scroll bar wrong.
	
As Simple as Possible
---------------------
* limit by screen size and operation
	* some component(like `Button` is bigger for better UX)
* render speed issue