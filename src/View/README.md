# View的解释
## View层架构
- View层主要的类是RakutenLinkMainView
- RakutenLinkMainView继承自AbstractViewPanel,不过这次作业这个抽象的View类应该不会有太多作用
- RakutenLinkBlock为连连看的一个个方块的类,继承自JLabel,保存有自己的行列信息
## View层接口
- ViewUpdatable定义了RakutenLinkMainView中可以供Controller调用的接口