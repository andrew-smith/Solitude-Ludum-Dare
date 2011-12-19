/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StartMenu.java
 *
 * Created on 19/12/2011, 11:31:53 AM
 */

package xandrew;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Andrew
 */
public class StartMenu extends javax.swing.JFrame {

    /** Creates new form StartMenu */
    public StartMenu() {
        initComponents();

        //center on screen
        setLocationRelativeTo(null);
        setResizable(false);

        //search for folders inside levels/
        File file = new File("levels/");

        for (File f : file.listFiles()) {
            if(f.isDirectory())
            {
                boolean dataPic = false, dispPic = false;
                //check files in that and ensure it has correct ones
                for (File innerF : f.listFiles()) {
                    if(innerF.isFile())
                    {
                        if(innerF.getName().contains("bg.png"))
                            dispPic = true;
                        if(innerF.getName().contains("data.png"))
                            dataPic = true;
                    }
                }

                if(dataPic && dispPic)
                    listOfLevels.add(f);

            }
        }


        cbxListLevels.setModel(new DefaultComboBoxModel(listOfLevels.toArray()));

        popupateTextFields();
    }

    private ArrayList<File> listOfLevels = new ArrayList<File>();


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxListLevels = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHowToPlay = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLevelDesigner = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAbout = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Solitude - Ludum Dare");

        cbxListLevels.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Select Level:");

        btnPlay.setText("Play!");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        txtHowToPlay.setBorder(null);
        txtHowToPlay.setEditable(false);
        txtHowToPlay.setOpaque(false);
        jScrollPane1.setViewportView(txtHowToPlay);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("How to Play", jPanel1);

        jScrollPane2.setBorder(null);

        txtLevelDesigner.setBorder(null);
        txtLevelDesigner.setEditable(false);
        txtLevelDesigner.setOpaque(false);
        jScrollPane2.setViewportView(txtLevelDesigner);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Level Designer", jPanel2);

        jScrollPane3.setBorder(null);

        txtAbout.setBorder(null);
        txtAbout.setEditable(false);
        txtAbout.setOpaque(false);
        jScrollPane3.setViewportView(txtAbout);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("About", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxListLevels, 0, 492, Short.MAX_VALUE))
                    .addComponent(btnPlay, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxListLevels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed


        File f = (File) cbxListLevels.getSelectedItem();
        if(f != null)
        {
            String nameTmp = f.getName();

            String[] arr = nameTmp.split("\\\\");
            String name = arr[arr.length-1];

            try
            {
                int levelID = Integer.parseInt(name);

                Alone.GAME_LEVEL = levelID;
                Alone.main(null);
            }
            catch(NumberFormatException ex)
            {
                //not a proper directory
            }
        }

    }//GEN-LAST:event_btnPlayActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPlay;
    private javax.swing.JComboBox cbxListLevels;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane txtAbout;
    private javax.swing.JTextPane txtHowToPlay;
    private javax.swing.JTextPane txtLevelDesigner;
    // End of variables declaration//GEN-END:variables

    private void popupateTextFields()
    {
        try
        {
            Scanner scan = new Scanner(new File("README"));

            String data = "";

            while(scan.hasNext())
                data += scan.nextLine() + "\n";

            String[] split = data.split("-------------------------------------------");

            txtHowToPlay.setText(split[0]);
            txtLevelDesigner.setText(split[1]);
            txtAbout.setText(split[2]);

            txtAbout.setCaretPosition(0);
            txtLevelDesigner.setCaretPosition(0);
            txtHowToPlay.setCaretPosition(0);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
