import java.util.Scanner;

public class Main {
    public static int newPriority(String i){
        if(i.matches("[-+]")){
            return 1;
        }else if(i.matches("[/*]")){
            return 2;
        }else if(i.matches("[(]")){
            return -1;
        }else{
            return 3;
        }
    }
    public static String afFixed(String elements[]) {
        StackChain<String> commands = new StackChain<String>();
        int priority = -1;
        String result = "";

        for (String i : elements) {
            boolean flagPriority = true;
            int currentPriority=-1;
            switch (i) {
                case "+":
                case "-":
                    currentPriority= 1;
                    flagPriority = false;
                case "*":
                case "/":
                    if (flagPriority)
                        currentPriority= 2;
                    flagPriority = false;

                case "^":
                    if (flagPriority)
                        currentPriority= 3;

                    if(commands.isEmpty() || currentPriority>priority){
                        priority = currentPriority;
                        commands.push(i);
                    }else { // DANDO ERRO -----------------------------------------------------------------------------

                        while(!commands.isEmpty() && !(currentPriority>priority)){
                            result+= commands.pop();
                            if(!commands.isEmpty()){
                                priority = newPriority(commands.peek());
                            }
                            if(priority<0){
                                break;
                            }
                        }
                        commands.push(i);
                        priority=currentPriority;

                    } //-----------------------------------------------------------------------------------------------
                    break;
                case ")":
                    while (!commands.isEmpty()&& !commands.peek().equals("(")){
                        result+= commands.pop();
                    }
                    commands.pop();
                    if(commands.isEmpty()){
                        priority=-1;
                    }else {
                        priority = newPriority(commands.peek());
                    }
                    break;
                case "(":
                    priority= -1;
                    commands.push(i);
                    break;
                default:
                    result+=i;
                    break;
            }
        }
        while(!commands.isEmpty()){
            result+=commands.pop();
        }
        return result;
    }

    public static void main(String[] args) { // function main -----------------------------------
        Scanner sc = new Scanner(System.in);

        int repeat = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < repeat; i++) {
            String input = sc.nextLine();
            String elements[] = input.split("");
            System.out.println(afFixed(elements));
        }
    }
}


// stack class
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
