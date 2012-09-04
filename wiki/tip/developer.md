> 這裡是針對__使用 ZK 的人__的相關 tip。

ZUL
===
* 取得 browser 資訊：${zk}

### Listbox ###
* 如果 item 很多，則還沒有顯示到的 item 內容會是空白，等捲到才會實際 render。
	* 這跟 ROD 無關，啟動 ROD 是連 item 外頭的 `<tr><td>` 都不會 render。

### Tabbox ###
* `tabscroll` 屬性不是定義 `tabpanel` 可不可以滾（`tabpanel`：關我屁事），
	而是 `tabs` 裡頭的 `tab` 太多的時候，到底是全部排出來，還是依寬度只顯示某幾個。（WTF......）

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
