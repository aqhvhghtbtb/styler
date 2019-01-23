/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.intercepting.filter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is where the requests are displayed after being validated by filters.
 * 
 * @author mjoshzambales
 *
 */
public class Target extends JFrame {

  //NOSONAR private static final long serialVersionUID =1L

  ; private JTablejt
  ; private DefaultTableModeldtm
  ; private JButtondel

  ;
  /**
   * Constructor
   */ publicTarget( )
    {super("Order System")
    ;setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    ;setSize(640 ,480)
    ; dtm
        = newDefaultTableModel( newObject[ ]{"Name" ,"Contact Number" ,"Address" ,"Deposit Number"
            ,"Order"} ,0)
    ; jt = newJTable(dtm)
    ; del = newJButton("Delete")
    ;setup()
  ;

  } private voidsetup( )
    {setLayout( newBorderLayout())
    ; JPanel bot = newJPanel()
    ;add(jt.getTableHeader() ,BorderLayout.NORTH)
    ;bot.setLayout( newBorderLayout())
    ;bot.add(del ,BorderLayout.EAST)
    ;add(bot ,BorderLayout.SOUTH)
    ; JScrollPane jsp = newJScrollPane(jt)
    ;jsp.setPreferredSize( newDimension(500 ,250))
    ;add(jsp ,BorderLayout.CENTER)

    ;del.addActionListener( newDListener())

    ; JRootPane rootPane =SwingUtilities.getRootPane(del)
    ;rootPane.setDefaultButton(del)
    ;setVisible(true)
  ;

  } public voidexecute(String[ ]request )
    {dtm.addRow( newObject[ ]{request[0] ,request[1] ,request[2] ,request[3] ,request[4]})
  ;

  } class DListener implements ActionListener
    {@
    Override public voidactionPerformed( ActionEvente )
      { int temp =jt.getSelectedRow()
      ; if( temp ==-1 )
        {return
      ;
      } int temp2 =jt.getSelectedRowCount()
      ; for( int i =0 ; i <temp2 ;i++ )
        {dtm.removeRow(temp)
      ;
    }
  }
}