import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.IllegalStateException;
import java.util.Queue;
import java.util.LinkedList;

public class BfTree {

    private static Queue<String> toks;

    private static boolean isEmpty() { return toks.isEmpty(); }
    private static String  next()    { return toks.element(); }
    private static void match(String tok) {
        if (isEmpty() || !next().equals(tok))
            throw new IllegalStateException("Error on match(" + tok + ")");
        toks.remove();
    }
    
    /* Parses a prefix of toks and returns reference to resulting parse tree. */
    /* Throws IllegalStateException if an error occurs.                      */
    private static DefaultMutableTreeNode parseS() {
        DefaultMutableTreeNode rval = new DefaultMutableTreeNode("S");
        if (isEmpty()) {
            // Make child be a leaf node labeled "" (empty string)
            rval.add(new DefaultMutableTreeNode(""));
        } else 
        if(next().equals("<")) { //S-> CS if next symbol is <
        rval.add(parseC());
        rval.add(parseS());
        }
        else 
            if(next().equals(">")) { //S->CS if next symbol is >
            rval.add(parseC());
           rval.add(parseS());
            }
        else 
            if(next().equals("+")) { //S->CS if next symbol is +
            rval.add(parseC());
            rval.add(parseS());    
            }
        else 
            if(next().equals("-")) { //S->CS if next symbol is -
            rval.add(parseC());
            rval.add(parseS());        
            }
        else 
            if(next().equals(",")) { //S->CS if next symbol is ,
            rval.add(parseC());
            rval.add(parseS());
            }
        else 
            if(next().equals(".")) { //S->CS if next symbol is .
            rval.add(parseC());
            rval.add(parseS());
            
            }  
         else 
         if(next().equals("[")) { //S->LS if next symbol is left bracket
         rval.add(parseL());
         rval.add(parseS());
                
                }
         else 
         if(next().equals("]")) { //S->null if next symbol is right bracket
        // rval.add(new DefaultMutableTreeNode("L"));
        // rval.add(parseL());
        // rval.add(new DefaultMutableTreeNode("S"));
            rval.add(new DefaultMutableTreeNode(""));
                
                }
        return rval;
    }

    /* Parses a prefix of toks and returns reference to resulting parse tree. */
    /* Throws IllegalStateException if an error occurs.                      */
    private static DefaultMutableTreeNode parseL() {
        DefaultMutableTreeNode rval = new DefaultMutableTreeNode("L");
     	if (next().equals("[")) {      
         match("[");
         rval.add(new DefaultMutableTreeNode("["));
        // rval.add(new DefaultMutableTreeNode("S"));
         rval.add(parseS());
         match("]");
         rval.add(new DefaultMutableTreeNode("]"));

     } 
     	else {
            // Unexpected token, so throw an exception
            throw new IllegalStateException("Unexpected: " + next());
        }
     	return rval;
    }

    /* Parses a prefix of toks and returns reference to resulting parse tree. */
    /* Throws IllegalStateException if an error occurs.                      */
    private static DefaultMutableTreeNode parseC() {
        DefaultMutableTreeNode rval = new DefaultMutableTreeNode("C");
         	if (next().equals("<")) {      
             match("<");
             rval.add(new DefaultMutableTreeNode("<"));
             
         } else 
         	if (next().equals(">")) {
             match(">");
             rval.add(new DefaultMutableTreeNode(">"));
             
         } else 
          	if (next().equals("+")) {
                match("+");
                rval.add(new DefaultMutableTreeNode("+"));
            }
          	else 
             	if (next().equals("-")) {
                 match("-");
                 rval.add(new DefaultMutableTreeNode("-"));
                 
             }
             	
             else 
                 if (next().equals(".")) {
                     match(".");
                     rval.add(new DefaultMutableTreeNode("."));
                     
                 }
                 else 
                  	if (next().equals(",")) {
                      match(",");
                      rval.add(new DefaultMutableTreeNode(","));
                      
                  }           
           else {
             // Unexpected token, so throw an exception
             throw new IllegalStateException("Unexpected: " + next());
         }
         return rval;
    }

    /* Converts "prog" to a Queue of legal Bf single-character tokens Strings.*/
    /* Parses all of toks and returns reference to resulting parse tree.      */
    /* Throws IllegalStateException if an error occurs.                       */
    public static DefaultMutableTreeNode parseBfToTree(String prog) {
        prog = prog.replaceAll("[^-+<>,\\.\\[\\]]","");  // Deletes all non-bf chars
        toks = new LinkedList<String>();
        for (char ch : prog.toCharArray())
            toks.add(Character.toString(ch));
        DefaultMutableTreeNode result = parseS();
        if ( ! isEmpty() )
            throw new IllegalStateException("Parse error");
        else
            return result;
    }

    public static void main(String[] args) {
        // You can write test code here
    }

}