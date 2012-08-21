### 測試 zul.xsd ###
* 將寫好的 `zul.xsd` 放在某個執行中的 web 目錄下，假設可以用 `http://localhost:8080/foo/zul.xsd` 連得到。
* 在有 code assist 的 XML editor（例如 Eclipse）開一個 `foo.xml` 檔案，檔案內容是：
	<zk xmlns="http://www.zkoss.org/2005/zul" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	 xsi:schemaLocation="http://www.zkoss.org/2005/zul http://localhost:8080/foo/zul.xsd ">
	</zk>
  理論上就可以讀到 zul.xsd 當中定義的語法。

如果在 Eclipse 下，有下列建議：
* 到 Preferences→Network Connections 下，把 Disable caching 給開啟。
* 每次修改 `zul.xsd` 之後，要關掉 `foo.xml`、重新開啟後（理論上）才會 load 到新的 `zul.xsd`。

[reference](http://books.zkoss.org/wiki/ZK_Installation_Guide/Setting_up_IDE/Eclipse_without_ZK_Studio)