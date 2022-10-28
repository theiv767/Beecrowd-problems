import java.util.Scanner;

public class Main {

    public static String test(int lenght, StaticQueue result) {
        boolean flag = true;
        StaticQueue<Integer> train = new StaticQueue<Integer>(lenght);
        StackChain<Integer> station = new StackChain<Integer>();
        for (int i = 1; i <= lenght; i++) {
            train.queue(i);
        }
        while (!result.isEmpty()) {
            if (!train.isEmpty() && (train.peek().equals(result.peek()))) {
                train.dequeue();
                result.dequeue();

            } else if (!station.isEmpty() && (station.peek().equals(result.peek()))) {
                station.pop();
                result.dequeue();

            } else {
                if (train.isEmpty()) {
                    flag = false;
                    break;
                }
                station.push(train.dequeue());
            }
        }
        if (flag)
            return "Yes";
        else
            return "No";

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int lenght = sc.nextInt();
        while(lenght != 0){
            int input = sc.nextInt();

            while(input != 0){
                StaticQueue<Integer> result = new StaticQueue<Integer>();
                result.queue(input);

                for(int i=1; i<lenght; i++){
                    result.queue(sc.nextInt());
                }
                System.out.println(test(lenght, result));
                input = sc.nextInt();
            }
            lenght = sc.nextInt();
            System.out.println();
        }
    }
}

//------------------------------------
//datta structures

// class stack
@SuppressWarnings("unchecked")
class StackChain<E> {
    private int size = 0;
    private Node lastElement = null;

    public void push(E value) {
        size++;
        Node adds = new Node(value);
        if (lastElement == null) {
            lastElement = adds;
        } else {
            adds.previous = lastElement;
            lastElement = adds;
        }
    }

    public E peek() {
        return (E) lastElement.value;
    }

    public E pop() {
        if (lastElement == null) {
            return null;
        }
        size--;
        E aux = (E) lastElement.value;
        lastElement = lastElement.previous;
        return aux;
    }

    public boolean isEmpty() {
        if (lastElement == null)
            return true;

        return false;
    }

    public int size() {
        return size;
    }

    //---------------------------
    class Node<E> {
        Node previous = null;
        E value;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value + "";
        }
    }

    @Override
    public String toString() {
        String showString = "";
        Node i = lastElement;

        while (i != null) {
            String adds = "";
            if (i.value.getClass() == String.class) {
                adds += "\"";
                adds += i;
                adds += "\"";
            } else {
                adds += i + "";
            }
            String aux = showString;
            showString = (i != lastElement) ? adds + ", " : adds;
            showString += aux;
            i = i.previous;
        }
        String aux = showString;
        showString = "[";
        showString += aux;
        showString += "]";

        return showString;
    }
}

// class queue
@SuppressWarnings("unchecked")
class StaticQueue<E> {
    private final int STANDART_LENGHT = 10;
    private int size = 0;
    private int firstIndex = 0;
    private int lastIndex = 0;
    Object[] elements;

    StaticQueue() {
        this.elements = new Object[STANDART_LENGHT];
    }

    StaticQueue(int initLenght) {
        this.elements = new Object[initLenght];
    }

    public void queue(E value) {
        if (isEmpty()) {
            size++;
            this.elements[0] = value;
            return;
        }
        if (lastIndex + 1 == firstIndex)
            incrementSize();

        try {
            lastIndex++;
            elements[lastIndex] = value;
        } catch (ArrayIndexOutOfBoundsException e) {
            if (firstIndex > 0) {
                lastIndex = 0;
                elements[lastIndex] = value;
            } else {
                lastIndex--;
                incrementSize();
                lastIndex++;
                elements[lastIndex] = value;
            }
        }
        size++;

    }

    public E dequeue() {
        if (!isEmpty()) {
            if (firstIndex == lastIndex) {
                E aux = (E) this.elements[firstIndex];
                clear();
                return aux;
            }
            size--;
            firstIndex++;
            return (E) this.elements[firstIndex - 1];
        }
        return null;
    }

    public E peek() {
        if (isEmpty())
            return null;

        return (E) this.elements[firstIndex];
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;

        return false;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        firstIndex = 0;
        lastIndex = 0;
        size = 0;
        this.elements = new Object[STANDART_LENGHT];

    }

    //------------------------------------
    public void incrementSize() {
        Object[] aux = new Object[(int) size + (size / 2)];

        int auxIndex = 0;
        for (int i = firstIndex; i != lastIndex; i++) {
            aux[auxIndex] = elements[i];
            auxIndex++;

            if (i + 1 >= elements.length)
                i = -1;
        }
        aux[auxIndex] = elements[lastIndex];
        firstIndex = 0;
        lastIndex = size - 1;
        this.elements = aux;
    }


    @Override
    public String toString() {
        String elementsString = "[";
        for (int i = firstIndex; i != lastIndex; i++) { 
            String adds = "";
            if (elements[i].getClass() == String.class) {
                adds += "\"";
                adds += this.elements[i];
                adds += "\"";
            } else {
                adds += this.elements[i] + "";
            }
            elementsString += adds + ", ";

            if (i + 1 >= elements.length)
                i = -1;
        }
        // add last element
        if (!isEmpty()) {
            String adds = "";
            if (elements[lastIndex].getClass() == String.class) {
                adds += "\"";
                adds += this.elements[lastIndex];
                adds += "\"";
            } else {
                adds += this.elements[lastIndex] + "";
            }
            elementsString += adds;
        }
        elementsString += "]";
        return elementsString;
    }
}
