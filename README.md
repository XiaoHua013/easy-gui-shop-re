## <img height="50" src="images/icon.png" width="50"/>EasyGuiShop



## 前言
EasyGuiShop原作者仓库现已无法访问：[链接](https://github.com/ZhangYang0204/easy-gui-shop/)  
因此我创建了这个存储库，本人不是此插件的作者，如果原作者想要删除此存储库，请联系我

## 许可证
我无法设置任何许可证，因为原作者就没设置  
如果原作者想要删除此存储库，请联系我

## 这是什么
- 一个简易的GUI商店插件
- 支持 1.8-1.20.4 Bukkit/Spigot/Paper/Folia
- 支持 中/英文 语言
- 支持 MySQL/SQLite 存储
- 可使用 Vault经济/PlayerPoints点券/物品 进行交易
- 玩家纯页面操作
- 玩家可创建店铺出售和收购商品
- 玩家可见每笔交易记录
- 玩家可收藏店铺增加店铺收藏数
- 店主可给店铺可购买人气
- 店主可以设置店铺描述
- 店主可以设置店铺实体位置坐标
- 店铺有浏览量
- 店铺可以被评论
- 店铺图标可用玩家头颅显示
- 商品可限制交易时间
- 商品可限制单次交易数量
- 页面具有搜索功能
- 交易支持税收
- 可设置存取物品货币的坐标
- 管理员打开玩家页面
- 管理员可将商品设置为无限数量
- 管理员可出售店铺图标
- 一键修复数据库里的无效数据
- 一键修正Yaml配置文件的配置项
  
## 下载
在 [Releases](https://github.com/WarSkyGod/easy-gui-shop/releases) 下载

## 依赖（以下可选）
- [Vault](https://www.spigotmc.org/resources/vault.34315/)（如果你想用经济交易，请务必再安装一个支持 Vault 的经济管理插件，比如EssentialsX）
- [PlayerPoints](https://www.spigotmc.org/resources/playerpoints.80745/)（如果你想用点券交易）
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)（如果你想要使用占位符）

## 命令与权限
**玩家**
- **/egs help**  
查看帮助
- **easyguishop.help**  
查看帮助权限
- **/egs openGui**  
打开Gui界面  
- **easyguishop.opengui**  
打开Gui界面权限
- **easyguishop.shopamount.<数量>**  
数量决定玩家可创建多少个店铺，比如 **easyguishop.shopamount.1** 就是允许玩家创建一个商店
- **easyguishop.shopnamelength.<长度>**  
长度决定玩家可设置多长的店铺名称，比如 **easyguishop.shopnamelength.10** 就是允许玩家设置10个字符的店铺名称
- **easyguishop.setgoodvaultprice**  
允许玩家设置商品的Vault价格
- **easyguishop.setgoodplayerpointsprice**  
允许玩家设置商品的点券价格
- **easyguishop.setgooditemprice**  
允许玩家设置商品以物易物交易

**管理员**
- **/egs openMui <玩家名字>**  
打开Mui界面（管理玩家的Gui商店）
- **easyguishop.openmui**  
打开Mui界面权限
- **/egs createIcon <图标名字>**  
增加图标，需要手持物品
- **easyguishop.createicon**  
增加图标权限
- **/egs deleteIcon <图标名字>**  
  删除图标
- **easyguishop.deleteicon**  
删除图标权限
- **/egs setIconName <图标名字> <新的名字>**
设置图标名字
- **easyguishop.seticonname**  
设置图标名字权限
- **/egs setIconVaultPrice <图标名字> <价格>**  
设置图标Vault价格
- **easyguishop.seticonvaultprice**  
设置图标Vault价格权限
- **/egs setIconPlayerPointsPrice <图标名字> <价格>**  
设置图标点券价格
- **easyguishop.seticonplayerpointsprice**  
设置图标点券价格权限
- **/egs setIconItemPrice <图标名字> <价格>**  
设置图标以物易物价格，需要手持物品
- **easyguishop.seticonitemprice**  
设置图标以物易物价格权限
- **/egs setIconStock <图标名字> <数量>**  
设置图标库存
- **easyguishop.seticonstock**  
设置图标库存权限
- **/egs setIconSystem <图标名字> <true|false>**  
设置图标系统补货
- **easyguishop.seticonsystem**  
设置图标系统补货权限
- **/egs setIconLimitTime <图标名字> <时间>**  
设置图标限购时间
- **easyguishop.seticonlimittime**  
设置图标限购时间权限
- **/egs resetIconLimitTime <图标名字>**  
重置图标限购时间
- **easyguishop.reseticonlimittime**  
重置图标限购时间权限
- **/egs setGoodSystem <店铺名字> <商品名字> <true|false>**  
设置商品系统补货
- **easyguishop.setgoodsystem**  
设置商品系统补货权限
- **/egs setShopSystem <店铺名字> <true|false>**  
设置店铺系统补货
- **easyguishop.setshopsystem**  
设置店铺系统补货权限
- **/egs plusShopPopularity <店铺名字> <数量>**  
增加店铺人气
- **easyguishop.plusshoppopularity**  
增加店铺人气权限
- **/egs subtractShopPopularity <店铺名字> <数量>**  
减少店铺人气
- **easyguishop.subtractshoppopularity**  
减少店铺人气权限
- **/egs correctYaml**  
对所有Yaml进行更正，不会自动载入，请执行后执行reload
- **easyguishop.correctyaml**  
更正Yaml权限
- **/egs correctDatabase**  
对数据库的一些数据进行修复，删掉不合法的数据（使用前请备份数据）
- **easyguishop.correctdatabase**  
修复数据库权限
- **/egs reloadPlugin**  
重新加载插件
- **easyguishop.reloadplugin**  
重新加载插件权限

## 展示
![image](images/图片展示1.png)
![image](images/图片展示2.png)
![image](images/图片展示3.png)
![image](images/图片展示4.png)
![image](images/图片展示5.png)
![image](images/图片展示6.png)
![image](images/图片展示7.png)
![image](images/图片展示8.png)
![image](images/图片展示9.png)
![image](images/图片展示10.png)
![image](images/图片展示11.png)
![image](images/图片展示12.png)
![image](images/图片展示13.png)
![image](images/图片展示14.png)
![image](images/图片展示15.png)

## 感谢
本插件使用了 [FoliaLib](https://github.com/handyplus/FoliaLib) 来做 **Folia** 兼容  
