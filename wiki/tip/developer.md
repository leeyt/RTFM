> 這裡是針對__使用 ZK 的人__的相關 tip。

ZUL
===

Common Java
===========
### Utility ###
* `Path.getComponent()` 可以用 id space 的路徑方式取得指定的 component。
* `Objects.equals()` 比對值是否相等，減少判斷 null 以及一些其他小細節的困擾。
* `Clients` 有許多 method 可以從 server side 要求 client side 作一些事情。包含偉大萬惡的 `Clients.evalJAvaScript()`（真xx的幹得好啊 XD）

