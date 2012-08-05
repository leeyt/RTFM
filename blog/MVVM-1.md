首先在 `src/fooPackage` 下新增一個 class，名字叫做 `MyViewModel`，內容是：

	package fooPackage;
	import org.zkoss.bind.annotation.Init;

	public class MyViewModel {
		private String bindF;
		private String loadF;
		private String saveF;

		public MyViewModel(){
			System.out.println("constructor");
		}

		@Init public void init(){
			System.out.println("init");
			bindF = "bindV";
			loadF = "loadV";
			saveF = "saveV";
		}

		public String getBindF() {
			System.out.println("getBindF");
			return bindF;
		}

		public void setBindF(String value) {
			System.out.print("setBindF : "+value);
			this.bindF = value;
			System.out.println("\t after set : "+this.bindF);
		}

		public String getLoadF() {
			System.out.println("getLoadF");
			return loadF;
		}

		public void setLoadF(String value) {
			System.out.print("setLoadF : "+value);
			this.loadF = value;
			System.out.println("\t after set : "+this.loadF);
		}

		public String getSaveF() {
			System.out.println("getSaveF");
			return saveF;
		}

		public void setSaveF(String value) {
			System.out.print("setSaveF : "+value);
			this.saveF = value;
			System.out.println("\t after set : "+this.saveF);
		}
	}

雖然看起來毫無反應、就只是個平凡的 Java POJO，不過在 ZK MVVM 中，我們把這樣的 class 稱為 **View Model**。接下來當然是寫一個 `index.zul`，內容為：

	<?page title="index.zul"?>
	<window apply="org.zkoss.bind.BindComposer" viewModel="@id('vm') @init('fooPackage.MyViewModel')">
		Bind Field : <textbox value="@bind(vm.bindF)" /> <separator />
		Load Field : <textbox value="@load(vm.loadF)" /> <separator />
		Save Field : <textbox value="@save(vm.bindF)" />
	</window>

瀏覽 `index.zul` 時，我們可以從 server 的 console 輸出發現下面這幾行：
> constructor  
> init  
> getBindF  
> getLoadF

畫面上則是在「Bind Field」與「Load Field」的欄位上分別出現「bindV」與「loadV」，而「Save Field」則仍然是空白，這代表什麼意思呢？

首先，ZK 在處理 `index.zul` 時，會依照 `viewModel` 的值，建立一個 `MyViewModel` 的 object，並給予變數名稱 `vm`，然後呼叫當中 annotation 為 `@init` 的 method（也就是 `init()`）。接著處理到 `@bind(vm.bindF)` 與 `@load(vm.loadF)` 時會呼叫 `MyViewModel` 的 `getBindF()` 與 `getLoadF()` 取得對應的值來顯示。那麼，`@bind` 與 `@load` 又有什麼區別呢？

現在回到瀏覽器上，將「bindV」後頭輸入「keyin」，當游標離開 textbox 時（觸發 onBlur），我們發現 server 的 console 輸出：
> setBindF : bindVKeyin     after set : bindVKeyin  
> getBindF

同樣對「loadF」輸入「Keyin」則完全沒反應，而對「saveF」輸入則出現：
> setSaveF : Keyin     after set : Keyin

你可能已經有些頭緒了，`@bind`、`@load`、`@save` 分別定義了 ZUL（View）與 ViewModel 之間資料傳遞的關係：
* `@load` 僅能讓 ZUL 從 ViewModel 取資料
* `@save` 則是僅能讓 ZUL 將資料傳回至 ViewModel
* `@bind` 則兩者均可。

那麼，如果想要在 ZUL 中更新 `@load` 的值，該怎麼作呢？答案是 `@NotifyChange` 這個 annotation。讓我們將 `MyViewModel` 的 field `loadF` 還有 `setLoadF()` 拿掉，然後把 `getLoadF()` 改成：

	public String getLoadF() {
		System.out.println("getLoadF");

		//reverse saveF
		StringBuffer result = new StringBuffer();
		for(int i=saveF.length()-1; i>=0; i--){
			result.append(saveF.charAt(i));
		}
		return result.toString();
	}

最後在 `setSaveF()` 前面加上 `@NotifyChange("loadF")`，重新瀏覽 `index.zul`，我們會發現「Load Field」的值一開始是「Vevas」（將 saveV 倒過來），如果你在「Save Field」當中輸入「123」，則「Load Field」會變成「321」 _（即使你很愛 ABBA 或是拿破崙，也不要輸入「ABBA」或是「able was i ere i saw elba」... 拜託 Orz）_ 。這是因為 `setSaveF()` 加上 `@NotifyChange("loadF")` 之後，只要呼叫到 `setSaveF()`（在這裡是「Save Field」的 textbox 觸發 onBlur），就會在執行完畢之後，重新觸發一次 `@load(vm.loadF)`，也就會呼叫 `getLoadF()` 會回傳新的「saveF」內容值的反字串。

`@NotifyChange` 後頭的參數還有幾種變形：`@NotifyChange({"bindF", "loadF"})`，當你一次想更新兩個以上的 field，就用這招。如果所有的 field 都想更新，又不想一個一個 keyin 進去怎麼辦？別害怕，有 `@NotifyChange("*")` 可以滿足你，不過在目前這個 case 中，跟 `@NotifyChange({“bindF”, “loadF”})` 的效果是一樣的。至於還有一個 `@NotifyChange(".")` 就留給你當回家作業了......
