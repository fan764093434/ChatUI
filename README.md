# 设计模式
## 使用意义
   * 结局特定场景的问题，比如管理Activity，使用单利模式、给RecyclerView添加头部和底部，使用装饰者模式
   * 有利于代码的规范，让代码更加灵活
   * 由于开发，提高代码的复用
## Java的常见集中设计模式学习笔记

#### 1.工厂模式
专门负责将大量有共同接口的类实例化。工厂模式可以动态决定将哪一个类实例化，不必事先知道每次要实例化哪一个类。

**工厂模式有以下几种形态：**

1. 简单工厂（Simple Factory）模式，又称静态工厂方法模式（Static Factory Method Pattern）。
    简单工厂模式是类的创建模式，又叫做静态工厂方法（Static Factory Method）模式。
    简单工厂模式是由一个工厂对象决定创建出那一种产品类 的实例。其实简单工厂模式就是由一个工
    厂类可以根据传入的参量决定创建出哪一种产品类的实例。<br/>
   实例：有三种水果，他们的父接口就是fruit<br/>
   葡萄 Grape<br/>
   草莓 Strawberry<br/>
   苹果 Apple<br/>
   ```
   public class FruitGardener {
       /**
       * 静态工厂方法
       */
       public static Fruit factory(String which) throws BadFruitException {
           if (which.equalsIgnoreCase("apple")){
                return new Apple();
           } else if (which.equalsIgnoreCase("strawberry")){
                return new Strawberry();
           } else if (which.equalsIgnoreCase("grape")){
                return new Grape();
           } else {
                throw new BadFruitException("Bad fruit request");
           }
       }
   ```
   在使用时，客户端只需调用FruitGardener 的静态方法factory()即可。<br/>
   使用缺点：对“开-闭”原则的支持不够，如果有新产品加入到系统中，就需要修复工厂类，将必要的逻辑代码加入到工厂类中。<br/>
   ![](https://github.com/fan764093434/Java-Design-pattern/blob/master/photo/simple-factory.png)<br/>
2. 工厂方法（Factory Method）模式，又称多态性工厂（Polymorphic Factory）模式或虚拟构造子（Virtual Constructor
   模式；是类的创建模式，它的目的是定义一个创建产品对象的工厂接口，将实际创建工作推迟到子类中，工厂方法模式是简单工厂模
   式的进一步抽象和推广，由于使用了多态性，工厂方法模式保持了简单工厂模式的优点，而且克服了简单工厂模式的缺点。
   在工厂方法模式中，核心的工厂类不在负责所有的产品创建，而是将具体的创创建工作交给子类去做，而核心类则成为一个抽象工厂
   角色，仅负责给出具体子类必须实现的接口，而不用接触那一产品实例化的细节。这种进一步的抽象化结果，使这种工厂方法模式可
   以用来允许系统在不修改具体工厂角色的情况下引进新的产品，这正是工厂方法模式优于简单工厂模式的优势所在。<br/>
   ![](https://github.com/fan764093434/Java-Design-pattern/blob/master/photo/factory-method-01.png)<br/>
   伪代码示例：
   ```
   //创建工厂类
   public interface Creator{
       /*** 工厂方法*/
       public Product factory();
   }
   抽象产品角色Product 类的源代码
   public interface Product{
   }
   具体工厂角色ConcreteCreator1 类的源代码
   public class ConcreteCreator1 implements Creator{
        /*** 工厂方法*/
       public Product factory(){
            return new ConcreteProduct1();
       }
   }
   具体工厂角色ConcreteCreator2 类的源代码
   public class ConcreteCreator2 implements Creator{
       /*** 工厂方法*/
       public Product factory(){
            return new ConcreteProduct2();
       }
   }
   具体产品角色ConcreteProduct1 类的源代码
   public class ConcreteProduct1 implements Product{

       public ConcreteProduct1(){
            //TODO do something
       }

   }
   具体产品角色ConcreteProduct2 类的源代码
   public class ConcreteProduct2 implements Product{

       public ConcreteProduct2(){
            //TODO do something
       }

   }
   客户端角色Client 类的源代码
   public class Client{

        private static Creator creator1, creator2;

        private static Product prod1, prod2;

        public static void main(String[] args) {
            creator1 = new ConcreteCreator1();
            prod1 = creator1.factory();
            creator2 = new ConcreteCreator2();
            prod2 = creator2.factory();
        }
   }
   ```
   注意：工厂方法模式在农场系统中的实现图<br/>
   ![](https://github.com/fan764093434/Java-Design-pattern/blob/master/photo/factory-method-02.png)
3. 抽象工厂（Abstract Factory）模式，又称工具箱（Kit 或Toolkit）模式。是所有形态的工厂模式中最为抽象和最具一般性
   的一种形态，他是工厂方法模式的进一步推广，简略图如下<br/>
   ![](https://github.com/fan764093434/Java-Design-pattern/blob/master/photo/abstract-factory-01.png)<br/>
   上图中左边的等级结构代表工厂的等级结构，右边两个等级结构分别代表两个不同产品的等级结构，抽象工厂模式可以向客户端提供
   一个接口，似的客户端在不必指定产品的具体类型下，创建多个产品族中的产品对象，<br/>

#### 2.模板设计模式
模板模式（Template Method）：是类的行为模式，准备一个抽象类，将部分逻辑以具体方法以及具体构造函数的形式实现，
然后声明一些抽象方法来迫使子类来实现剩下的逻辑方法，不同的子类可以有不同的实现形式，从而对剩余的逻辑有不同的实现，
这就是模板模式的用意。<br/>
**代码实例：**
```
public abstract class AbstractClass{

    public void TemplateMethod() {   //先实现一个模板方法，这是子类都要用的方法
        doOperation1();
        doOperation2();
        doOperation3();
    }

    protected abstract void doOperation1();//定义抽象方法，需要不同的子类自己完成

    protected abstract void doOperation2();

    private final void doOperation3(){
        //do something
    }
}


public class ConcreteClass extends AbstractClass { //继承抽象类，实现抽象方法

    public void doOperation1() {
        //write your code here
        System.out.println("doOperation1();");
    }

    public void doOperation2() {
        //The following should not happen:
        //doOperation3();
        //write your code here
        System.out.println("doOperation2();");
    }
}
```
在java中这是常用的技巧，可以解决代码复用的问题。

