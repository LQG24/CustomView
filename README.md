# CustomView
#  自定义view相关笔记学习
################################绘图篇################################
#SimgleDraw 概述及基本几何图形绘制：增加文字居中控件绘制
 
# MyRegionView 自定义控件区域（Range）
#canvas变换与操作：画布平移、旋转、缩放、扭曲、clip裁剪  save（每次调用Save（）函数，都会把当前的画布的状态进行保存，然后放入特定的栈中）
  #restore每当调用Restore（）函数，就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画。
  #https://blog.csdn.net/harvic880925/article/details/39080931
  
# DrawText:drawText()详解  关于drawText的四线格与FontMetrics
#ScrollerLayout 用Scroller实现View的弹性滑动
#CustomViewPage 用Scroller实现ViewPage卡片切换功能

################################动画篇################################
#PointView 用valueAnimation Evalutor实现自绘并加动效，还增加ARGB渐变
#OAnimPointView  objectAnimation实现自绘并加动效

##########################视图篇################################
#TestLayout 自定义ViewGroup 实现添加子view和支持设置margin
#FlowLayout 自定义ViewGroup 实现自适应容器实现
#LinearItemDecoration 实现ItemDecoration与蒙版效果
#CustomLayoutManager 自定义LayoutManager 实现列表展示 并且支持上下滑动
#ConstraintActivity app:layout_constraintHorizontal_bias和app:layout_constrainedWidth 结合使用
                     #实现回收复用
                     
#########################事件冲突###############################
#包名下event_conflict 外部拦截法和内部拦截法
#包名下srl_vp SwipeRefreshLayout和ViewPage事件冲突 外部拦截法和内部拦截法
#包名下NestedScrolling 同个方向的滑动嵌套NestedScrollingParent NestedScrollingChild
#NestedTraditionActivity里边 NestedTraditionLayout实现NestedScrollingParent滑动嵌套 
#CustomBehaviorActivity 自定义Behavior的嵌套滑动交互效果
#CustomLifeView 自定义View来观察View与Activity生命周期的变化