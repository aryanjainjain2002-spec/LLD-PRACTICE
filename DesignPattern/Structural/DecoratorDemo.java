
interface TextView {
    String render();
}

class SimpleTextView implements TextView {
    private String text;

    SimpleTextView(String text){
        this.text = text;
    }

    @Override
    public String render(){
        System.out.println(text);
        return text;
    }
}


abstract class Decorator implements TextView{
    protected TextView obj;

    Decorator(TextView obj){
        this.obj = obj;
    }
}


class BoldTextDecorator extends Decorator{

    BoldTextDecorator(TextView obj){
        super(obj);
    }

    @Override
    public String render(){
        return "<b>" + obj.render() + "</b>";
    }
}

class ItalicTextDecorator extends Decorator{

    ItalicTextDecorator(TextView obj){
        super(obj);
    }

    @Override
    public String render(){
        return "<i>" + obj.render() + "</i>";
    }
}


class UnderlinedTextDecorator extends Decorator{

    UnderlinedTextDecorator(TextView obj){
        super(obj);
    }

    @Override
    public String render(){
        return "<u>" + obj.render() + "</u>";
    }
}

public class DecoratorDemo{

    public static void main(String [] args){
        TextView textView = new SimpleTextView("Aryan is here.");

        System.out.println(textView.render());

        TextView boldItalicText = new BoldTextDecorator(new ItalicTextDecorator(textView));

        System.out.println(boldItalicText.render());

    }
}