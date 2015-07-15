# Qupai-SDK

目录为趣拍SDK的Demo：

1.QupaiSdk为需要的依赖SDK包其中包含一系列的jar包和so包
  
  导入前需要先编译：https://github.com/qupai/qupai-media-thirdparty
  
  将生成的qupai-media-thirdparty.so 拷贝到QupaiSdk\libs\armeabi-v7a\目录下。----这里为开发者自行编译，编译脚本已经写好.有问题可以提交

2.appcompat_v7,和recyclerview 为需要依赖的库。这为了方便开发者都给出来。

3.导入配置的project.properties，都是相对路径，项目不移动的前提想应该是直接导入就可以用的.

4.QupaiDemo 为我们给开发者的Demo以供使用参考。导入后请自行替换百川sdk需要做的验证图片。
  
  百川sdk下载步骤请参考：http://baichuan.taobao.com/ 进入注册之后下载选择趣拍，得到SDK.

5.有任何问题可以加入趣拍SDK QQ群：422053769 