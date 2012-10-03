> # 不務正業之 JSF 心得 #

* JSF：毫無反應，就是一組規格。
	* [Facelets Javadoc][JSF Javadoc]→其實不太懂這個跟 Mojarra 的差別？
* Mojarra：根據 JSF 所實做出來的 web 作品
	* [Javadoc][Mojarra Javadoc]
* PrimeFaces：毫無反應，就是一沱 base on Mojarra 的 Component 集合（還有一些附帶產品）

[JSF Javadoc]: http://docs.oracle.com/javaee/6/javaserverfaces/2.1/docs/vdldocs/facelets/
[Mojarra Javadoc]: http://javaserverfaces.java.net/users.html

### EL 保留字 ###
* cc：composite component 的保留字

### Composite Component ###
樣本檔案必須放在 resources 底下，如果是 `resources/foo` 那麼套用的頁面（官方名詞：using page）的 xmlns 要宣告成

	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:em="http://java.sun.com/jsf/composite/foo/">

坦白說還蠻討厭的 XD

