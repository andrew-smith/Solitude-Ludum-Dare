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
import java.util.ArrayList;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxListLevels, 0, 97, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    // End of variables declaration//GEN-END:variables

}
