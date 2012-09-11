> 這裡是針對__使用 ZK 的人__的相關 tip。

ZUL
===
* 取得 browser 資訊：`${zk}`

MVVM
----
有一個 `Listbox` 要設定 `selectedIndex`，但是有可能是空的，所以得要在空的時候設定成 -1，那麼寫成這樣是不行的：
	
	<listbox selectedIndex='@load(vm.listEmpty ? vm.emptyIndex : vm.selectedIndex) @save(vm.selectedIndex)' >

必須得要寫成

	<listbox model="@load(vm.list)" emptyMessage="Is Empty!"
	 selectedIndex='@load(vm.listEmpty ? vm.emptyIndex : vm.selectedIndex) @save(vm.selectedIndex)' >

還蠻合情合理的。另外還有兩個哏：

* 奇怪，明明 `vm.list` 是個 `java.util.List` 有 `isEmpty()`，但是卻不能用 `vm.list.empty`
* 寫成 `@load(vm.listEmpty ? 1 : vm.selectedIndex)` 會炸「不能從 long 轉成 integer」的錯誤訊息

Component
---------
### Listbox ###
* 如果 item 很多，則還沒有顯示到的 item 內容會是空白，等捲到才會實際 render。
	* 這跟 ROD 無關，啟動 ROD 是連 item 外頭的 `<tr><td>` 都不會 render。

### Tabbox ###
* `tabscroll` 屬性不是定義 `tabpanel` 可不可以滾（`tabpanel`：關我屁事），
	而是 `tabs` 裡頭的 `tab` 太多的時候，到底是全部排出來，還是依寬度只顯示某幾個。（WTF......）

Tag Library
-----------
在 zweb 這個 project 當中...... 

Common Java
===========
### Utility ###
* `Path.getComponent()` 可以用 id space 的路徑方式取得指定的 component。
* `Objects.equals()` 比對值是否相等，減少判斷 null 以及一些其他小細節的困擾。
* `Clients` 有許多 method 可以從 server side 要求 client side 作一些事情。包含偉大萬惡的 `Clients.evalJAvaScript()`（真xx的幹得好啊 XD）

### Tabelt ###
* 在 .java 當中判斷是不是 tablet。第一行的條件必須滿足，正常情況（VM、Model）應該都取得到。

	ServletRequest request = ServletFns.getCurrentRequest();
	mobile = Servlets.getBrowser(request, "mobile") != null;
