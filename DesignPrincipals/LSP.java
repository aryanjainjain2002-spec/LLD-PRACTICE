
//subtype should be substituted for base type with altering the expected behaviour of program.

// if we are overriding some child class method to just throw an error or adding specific conditions for subtype in client code ---> violation of LSP

//True Polymorphis(Client just should not know about subtype)

//EXAMPLE: below implementation breaking LSP
//assumtion all the documents are editable

// // class Document {
// //     protected String data;

// //     public Document(String data){
// //         this.data = data;
// //     }

// //     public String getData() {
// //         return data;
// //     }

// //     public void save(String data){
// //         this.data = data;
// //     }

// //     public void open(){
// //         System.out.println("The Content is : " + data);
// //     }
// // }

// class ReadOnlyDocument extends Document {

//     public ReadOnlyDocument(String data){
//         super(data);
//     }

//     @Override
//     public void save(String data){
//         throw new UnsupportedOperationException("ReadOnly Data Cannot be saved!");
//     }
// }


interface Document {
    void open();
    String getData();
}

interface Editable extends Document {
    void save(String data);
}

class ReadOnlyDocument implements Document {
    protected String data;

    ReadOnlyDocument(String data){
        this.data = data;
    }

    @Override
    public void open(){
        System.out.println("Read Only Document Opned. Data : " + preview());
    }

    @Override
    public String getData(){
        return this.data;
    }

    private String preview(){
       return data.substring(0,Math.min(data.length(),20)) + "....";
    }
}

class EditableDocument implements Editable {
    protected String data;

    EditableDocument(String data){
        this.data = data;
    }

    @Override
    public void open(){
        System.out.println("Editable Document Opned. Data : " + preview());
    }

     @Override
    public String getData(){
        return this.data;
    }

    @Override
    public void save(String data){
        this.data = data;
    }


    private String preview(){
       return data.substring(0,Math.min(data.length(),20)) + "....";
    }
}

class DocumentProcessor {
    public void process(Document doc) {
        doc.open();
        System.out.println("Document processed.");
    }

    public void processAndSave(Editable doc, String additionalInfo) {
        doc.open();
        String currentData = doc.getData();
        String newData = currentData + " | Processed: " + additionalInfo;
        doc.save(newData);
        System.out.println("Editable document processed and saved.");
    }
}


//Subtype should except more then base class in form of inputs.
//subtype should return less then base class to follow the LSP(in terms of range)

//Consistency should be there any assumption by client on base class should be followed on the child classes

//EXAMPLE: SHAPE -> RECTANGLE AND SQUARE

interface Shape{
    int getArea();//need volume for 3d shapes
}

class Rectangle implements Shape {
    private int width;
    private int height;

    Rectangle(int h , int w){
        this.height = h;
        this.width = w;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public int getArea(){
        return height * width ;
    }
}

class Square implements Shape {
    private int side;

    Square(int side){
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    @Override
    public int getArea() {
        
        return side * side;
    }
}


public class LSP {
    
}
