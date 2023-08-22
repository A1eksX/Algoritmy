package Algoritmy;

// Левостороннее красно-черное дерево.
import java.util.Scanner;

class node {
    node left, right;
    int data;

    // красный ==> true, черный ==> false
    boolean color;

    node(int data) {    // конструктор
        this.data = data;
        left = null;
        right = null;

// Новый узел, который создается, является всегда красного цвета.
        color = true;
    }
}

public class LLRBTREE {

    private static node root = null;

    // Функция для поворота узла против часовой стрелки.
    node rotateLeft(node myNode) {
        System.out.printf("поворот влево!!\n");
        node child = myNode.right;  // объявляем переменную где будет храниться правый ребёнок
        node childLeft = child.left;    // объявляем переменную где будет храниться левый ребёнок правого ребёнка (внук)

        child.left = myNode;    // к ребёнку слева цепляем деда
        myNode.right = childLeft;   // к деду справа внука

        return child;   // возвращаем ребёнка
    }

    // Функция для поворота узла по часовой стрелке.
    node rotateRight(node myNode) {
        System.out.printf("вращение вправо\n");
        node child = myNode.left;   // объявляем переменную где будет храниться левый ребёнок
        node childRight = child.right;  // объявляем переменную где будет храниться правый ребёнок левого ребёнка (внук)

        child.right = myNode;   // к ребёнку справа цепляем деда
        myNode.left = childRight;   // к деду слева внука

        return child;   // возвращаем ребёнка
    }

    // Функция для проверки того, является ли узел красного цвета или нет.
    boolean isRed(node myNode) {
        if (myNode == null) {
            return false;
        }
        return (myNode.color == true);  // возвращаем красный цвет
    }

    // Функция для изменения цвета двух узлов. Тут обычный метод замены через третью переменную
    void swapColors(node node1, node node2) {
        boolean temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
    }

    // вставка в левостороннее Красно-черное дерево.
    node insert(node myNode, int data) {
// Обычный код вставки для любого двоичного файла
        if (myNode == null) {   // если узла нет ...
            return new node(data);  // ... создаём
        }

        if (data < myNode.data) {   // если новое значение меньше значения родителя
            myNode.left = insert(myNode.left, data);    // ставим слева
        } else if (data > myNode.data) {    // если новое значение больше значения родителя
            myNode.right = insert(myNode.right, data);  // ставим справа
        } else {
            return myNode;  // если такое значение есть, не создаём узлы
        }

// случай 1.
        // когда правый дочерний элемент красный, а левый дочерний элемент черный или не существует.
        if (isRed(myNode.right) && !isRed(myNode.left)) {
// Повернуть узел влево, т.к. в левостороннем дереве красных детей справа быть не должно
            myNode = rotateLeft(myNode);

// Поменять местами, цвет дочернего узла всегда должен быть красным
            swapColors(myNode, myNode.left);
        }

// случай 2
        // левый ребёнок и левый внук красные
        if (isRed(myNode.left) && isRed(myNode.left.left)) {
// Повернуть узел в право
            myNode = rotateRight(myNode);
            swapColors(myNode, myNode.right);
        }

// случай 3
        // когда и левый, и правый дочерние элементы окрашены в красный цвет.
        if (isRed(myNode.left) && isRed(myNode.right)) {
// Инвертировать цвет узла это левый и правый дети.
            myNode.color = !myNode.color;

// Изменить цвет на черный.
            myNode.left.color = false;
            myNode.right.color = false;
        }

        return myNode;
    }

    // Обход по порядку
    void inorder(node node) {
        if (node != null)   // если родитель существует
        {
            inorder(node.left); // идём влево
            char c = '☻';   // при выводе печатаем цвет узла - красный
            if (node.color == false)
                c = '☺';    // при выводе печатаем цвет узла - чёрный
            System.out.print(node.data + ""+c+" "); // выводим в консоль
            inorder(node.right);    // переходим на правую сторону
        }
    }

    public static void main(String[] args) {
        // запускаем программу
        LLRBTREE node = new LLRBTREE();
        Scanner scan = new Scanner(System.in);

        char ch;
        do {
            System.out.println("Введите целое число");

            int num = scan.nextInt();
            root = node.insert(root, num);

            node.inorder(root);
            System.out.println("\nВы хотите продолжить? (введите y или n)");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
}