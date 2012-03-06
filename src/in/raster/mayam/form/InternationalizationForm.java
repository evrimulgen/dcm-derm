/* ***** BEGIN LICENSE BLOCK *****
* Version: MPL 1.1/GPL 2.0/LGPL 2.1
*
* The contents of this file are subject to the Mozilla Public License Version
* 1.1 (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
* for the specific language governing rights and limitations under the
* License.
*
*
* The Initial Developer of the Original Code is
* Raster Images
* Portions created by the Initial Developer are Copyright (C) 2009-2010
* the Initial Developer. All Rights Reserved.
*
* Contributor(s):
* Babu Hussain A
* Meer Asgar Hussain B
* Prakash J
* Suresh V
*
* Alternatively, the contents of this file may be used under the terms of
* either the GNU General Public License Version 2 or later (the "GPL"), or
* the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
* in which case the provisions of the GPL or the LGPL are applicable instead
* of those above. If you wish to allow use of your version of this file only
* under the terms of either the GPL or the LGPL, and not to allow others to
* use your version of this file under the terms of the MPL, indicate your
* decision by deleting the provisions above and replace them with the notice
* and other provisions required by the GPL or the LGPL. If you do not delete
* the provisions above, a recipient may use your version of this file under
* the terms of any one of the MPL, the GPL or the LGPL.
*
* ***** END LICENSE BLOCK ***** */
package in.raster.mayam.form;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.model.combo.CountryComboModel;
import in.raster.mayam.model.combo.LanguageComboModel;
import in.raster.mayam.model.combo.LocaleComboModel;

/**
 *
 * @author  BabuHussain
 * @version 0.9
 *
 */
public class InternationalizationForm extends javax.swing.JPanel {

    /** Creates new form InternationalizationForm */
    public InternationalizationForm() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        localeLabel = new javax.swing.JLabel();
        localeCombo = new javax.swing.JComboBox();
        restartMessageLabel = new javax.swing.JLabel();
        headerLabel = new javax.swing.JLabel();
        countryLabel = new javax.swing.JLabel();
        countryCombo = new javax.swing.JComboBox();
        languageLabel = new javax.swing.JLabel();
        languageCombo = new javax.swing.JComboBox();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle",ApplicationContext.currentLocale); // NOI18N
        localeLabel.setText(bundle.getString("InternationalizationForm.localeLabel.text_1")); // NOI18N

        localeCombo.setModel(new LocaleComboModel(ApplicationContext.databaseRef.getLocaleIDForSelectedCountryAndlanguage((String)countryCombo.getSelectedItem(),(String)languageCombo.getSelectedItem())));
        localeCombo.getModel().setSelectedItem((String)ApplicationContext.databaseRef.getActiveLanguageAndCountry()[4]);
        localeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localeComboActionPerformed(evt);
            }
        });

        restartMessageLabel.setForeground(java.awt.Color.green);
        restartMessageLabel.setText(bundle.getString("InternationalizationForm.restartMessageLabel.text_1")); // NOI18N

        headerLabel.setBackground(new java.awt.Color(0, 0, 0));
        headerLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14));
        headerLabel.setForeground(new java.awt.Color(255, 138, 0));
        headerLabel.setText(bundle.getString("InternationalizationForm.headerLabel.text_1")); // NOI18N
        headerLabel.setOpaque(true);

        countryLabel.setText(bundle.getString("InternationalizationForm.countryLabel.text_1")); // NOI18N

        countryCombo.setModel(new CountryComboModel(ApplicationContext.databaseRef.getCountryListForLocale()));
        countryCombo.setPreferredSize(new java.awt.Dimension(47, 35));
        countryCombo.getModel().setSelectedItem((String)ApplicationContext.databaseRef.getActiveLanguageAndCountry()[2]);
        countryCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                countryComboItemStateChanged(evt);
            }
        });

        languageLabel.setText(bundle.getString("InternationalizationForm.languageLabel.text_1")); // NOI18N

        languageCombo.setModel(new LanguageComboModel(ApplicationContext.databaseRef.getLanguageListForSelectedCountry((String)countryCombo.getSelectedItem())));
        languageCombo.getModel().setSelectedItem((String)ApplicationContext.databaseRef.getActiveLanguageAndCountry()[3]);
        languageCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                languageComboItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, headerLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(77, 77, 77)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(restartMessageLabel)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(languageLabel)
                            .add(countryLabel)
                            .add(localeLabel))
                        .add(30, 30, 30)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(localeCombo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(languageCombo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(countryCombo, 0, 212, Short.MAX_VALUE))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(headerLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(countryLabel)
                    .add(countryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(languageLabel)
                    .add(languageCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(localeCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(localeLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 122, Short.MAX_VALUE)
                .add(restartMessageLabel)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {countryCombo, languageCombo, localeCombo}, org.jdesktop.layout.GroupLayout.VERTICAL);

    }// </editor-fold>//GEN-END:initComponents

    private void localeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localeComboActionPerformed
        updateLocaleToDatabase();
    }//GEN-LAST:event_localeComboActionPerformed

    private void countryComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_countryComboItemStateChanged
          setLanguageInfo();
    }//GEN-LAST:event_countryComboItemStateChanged

    private void languageComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_languageComboItemStateChanged
           setLocale();
    }//GEN-LAST:event_languageComboItemStateChanged

    private void setLocale()
    {
        localeCombo.setModel(new LocaleComboModel(ApplicationContext.databaseRef.getLocaleIDForSelectedCountryAndlanguage((String)countryCombo.getSelectedItem(),(String)languageCombo.getSelectedItem())));
        localeCombo.setSelectedIndex(0);
    }
    private void setLanguageInfo()
    {
        languageCombo.setModel(new LanguageComboModel(ApplicationContext.databaseRef.getLanguageListForSelectedCountry((String)countryCombo.getSelectedItem())));
        languageCombo.setSelectedIndex(0);
    }

    private void updateLocaleToDatabase()
            {        
            ApplicationContext.databaseRef.updateDefaultLocale((String)localeCombo.getSelectedItem());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox countryCombo;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JComboBox languageCombo;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JComboBox localeCombo;
    private javax.swing.JLabel localeLabel;
    private javax.swing.JLabel restartMessageLabel;
    // End of variables declaration//GEN-END:variables

}
