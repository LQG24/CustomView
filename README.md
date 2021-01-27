# CustomView
//自定义view相关笔记学习
################################绘图篇################################
#SimgleDraw 概述及基本几何图形绘制
# MyRegionView 自定义控件区域（Range）
#canvas变换与操作：画布平移、旋转、缩放、扭曲、clip裁剪  save（每次调用Save（）函数，都会把当前的画布的状态进行保存，然后放入特定的栈中）
  #restore每当调用Restore（）函数，就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画。
  #https://blog.csdn.net/harvic880925/article/details/39080931
  
# DrawText:drawText()详解  关于drawText的四线格与FontMetrics

################################动画篇################################
#PointView 用valueAnimation Evalutor实现自绘并加动效，还增加ARGB渐变
#OAnimPointView  objectAnimation实现自绘并加动效