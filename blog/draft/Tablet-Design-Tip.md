Scrolling
=========

Principle
---------
* On tablet, container's size must smaller than it's content. 

A true example
--------------
* transfer a standard layout pattern to tablet.

Component Support
-----------------
* enable
	* Borderlayout : autoScroll
	* Deteail(Grid), Groupbox, Window : contentStyle="overflow:auto"
	* PanelChildren, Tabpanel :  style="overflow:auto"
	* ScrollView
	* scroll by browser
* disable
	* Grid, Listbox, Tree : (disable) xmlns:ca="client/attribute" ca:data-scrollable="false"

雜項 tip
--------
* parent 跟 child 最好只允許一個可以滾動
* `TextArea`（`Textbox` 的 `multiline="true"`） 的 scroll 不是 ZK 控制的，所以底層最好不要可以滾動

Other issue
===========

Mold Support
------------
* provide a table or a list like http://books.zkoss.org/wiki/ZK_Component_Reference/Tablet_Devices/Unsupported_Molds

As Simple as Possible
---------------------
* render speed issue