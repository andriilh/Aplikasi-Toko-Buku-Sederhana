/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kasir;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import static com.sun.javafx.application.PlatformImpl.exit;

/**
 *
 * @author Andri
 */
public class TulisKu extends javax.swing.JFrame {

    char type;
    DefaultTableModel tableModel;
    String Daftar;
    long hargaSatuan = 0, jumlah = 0, totalBayar = 0, total = 0;
    DecimalFormat df = new DecimalFormat("#,###");
    
    public TulisKu() {
        initComponents();
        
        tableModel = (DefaultTableModel) tblpesanan.getModel();
        tableModel .setRowCount(0);
        jjumlah.setText(null);
        jbayar.setText(null);
        temptot.setText(""+0);
        totalharga.setText("Rp. 0-,");
        totalbayar.setText("Rp. 0-,");
        
        jgntampil();
        radio();
        nomortransaksi();
    }
    
    public void nomortransaksi(){
        long noTransaksi = System.currentTimeMillis();
        ntrans.setText(String.valueOf(noTransaksi));
    }
    
    private void jgntampil(){
        dtl.setVisible(false);
        haapus.setVisible(false);
        ulng.setVisible(false);
        metu.setVisible(false);
        temptot.setVisible(false);
        tkn.setVisible(false);
    }

    public void harga(Long harga){
        
        Daftar = listdaftar.getSelectedValue().toString();
        hargaSatuan = harga;
        jumlah = Long.parseLong(jjumlah.getText());
        total = hargaSatuan*jumlah;
                
        Object[] data ={Daftar,hargaSatuan,jumlah,total};
        tableModel.addRow(data);
    }
    
    public void transaksi(){
        if (listdaftar.isSelectionEmpty()){
            JOptionPane.showMessageDialog(null,"Pesanan Belum Dipilih, silahkan pilih pesanan terlebih dahulu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
       
        
        if (jjumlah.getText().isEmpty()){
            jjumlah.requestFocus();
            JOptionPane.showMessageDialog(null, "Jumlah Pesanan Belum Diisi, silahkan isi terlebih dahulu", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        
        int ljumlah = Integer.parseInt(jjumlah.getText());
        int dftr = listdaftar.getSelectedIndex();
        if (!jjumlah.getText().isEmpty() && !listdaftar.isSelectionEmpty() && ljumlah<= 50){
            if(dftr == 0){
                harga(Long.valueOf(30000));                
            }
            if(dftr == 1){
                harga(Long.valueOf(12000));
            }
            if(dftr == 2){
                harga(Long.valueOf(8000));
            }
            if(dftr == 3){
                harga(Long.valueOf(3500));
            }
            if(dftr == 4){
                harga(Long.valueOf(66750));
            }
            if(dftr == 5){
                int jmlh = Integer.parseInt(jjumlah.getText());
                if (jmlh == 1 ){
                    harga(Long.valueOf(120000));
                }
                else if(jmlh >= 2){
                    harga(Long.valueOf(100000));
                }                
            }
            if(dftr == 6){
                harga(Long.valueOf(14500));
            }
            if(dftr == 7){
                harga(Long.valueOf(45000));
            }
            if(dftr == 8){
                harga(Long.valueOf(55000));
            }
            if(dftr == 9){
                harga(Long.valueOf(75000));
            }
            
        } else {
            JOptionPane.showMessageDialog(null,"Maksimal Pembelian 50 buah. silahkan Input kembali","Peringatan",JOptionPane.OK_OPTION);
            jjumlah.setText(null);
            jjumlah.requestFocus();
            listdaftar.isSelectionEmpty();
        }
        
        total();
        radio();
        debit();
        tunai();  
    }
    
    public void radio(){
        double tmp = Double.parseDouble(temptot.getText());
        if(tmp <= 0){
            debit.setEnabled(false);
            tunai.setEnabled(false);
            jbayar.setEnabled(false);
            rdb.clearSelection();
        } if(tmp > 0){
            debit.setEnabled(true);
            tunai.setEnabled(true);
        }
    }
    
    public void total(){
        long tempTotalBayar=0;
            for(int i=0; i<tableModel.getRowCount();i++){
                tempTotalBayar = tempTotalBayar+Long.parseLong(tableModel.getValueAt(i,3).toString());
                totalBayar = tempTotalBayar;
                temptot.setText(""+totalBayar);                
                totalharga.setText("Rp. "+df.format(totalBayar)+",-");                
                jjumlah.setText("");
                listdaftar.clearSelection();
            }
            
            if(tableModel.getRowCount()==0){
                totalbayar.setText("Rp. "+df.format(0)+",-");
                temptot.setText("0");
//                totalharga.setText("Rp. "+df.format(0)+",-");
            }
        
            tunai();
            debit();            
    }
    public void debit(){
        if(debit.isSelected()){
            
            long a = Long.parseLong(temptot.getText());
            long diskon = (long) (a * 0.10);
            long hargabayar = a-diskon;
            
            jdiskon.setText("10% (Rp. "+df.format(diskon)+",-)");
            
            jdiskon.setForeground(new java.awt.Color(153,255,102));
            temptot.setText(""+hargabayar);
            totalbayar.setText("Rp. "+df.format(hargabayar)+",-");            
            tunai.setEnabled(true);
            debit.setBackground(new java.awt.Color(0,51,51));
            tunai.setBackground(new java.awt.Color(0,102,102));
            debit.setEnabled(false);
            
            jLabel7.setVisible(false);
            
            jbayar.setText(null);
            
        } 
    }
    
    public void tunai(){
        if(tunai.isSelected()){
            jdiskon.setText("0%");
            jdiskon.setForeground(new java.awt.Color(0,0,0));
            long tempTotalBayar=0;
            for(int i=0; i<tableModel.getRowCount();i++){
                tempTotalBayar = tempTotalBayar+Long.parseLong(tableModel.getValueAt(i,3).toString());
                totalBayar = tempTotalBayar;
                temptot.setText(""+totalBayar);                
                totalbayar.setText("Rp. "+df.format(totalBayar)+",-");
                totalharga.setText("Rp. "+df.format(totalBayar)+",-");                
                jjumlah.setText("");
                listdaftar.clearSelection();
            }    
            
            
            tunai.setEnabled(false);
            debit.setEnabled(true);
            tunai.setBackground(new java.awt.Color(0,51,51));
            debit.setBackground(new java.awt.Color(0,102,102));
            
            jbayar.requestFocus();
            jLabel7.setVisible(true);
            
        } 
    }
    
    public void hpsbaris(){
            DefaultTableModel model = (DefaultTableModel) tblpesanan.getModel();        
            int SelectedRowIndex = tblpesanan.getSelectedRow();
            if(SelectedRowIndex < 0){
                JOptionPane.showMessageDialog(this, "Silahkan pilih barang yang akan di hapus!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            if(SelectedRowIndex >= 0){
                if(JOptionPane.showConfirmDialog(this,"Anda yakin untuk menghapus?","Konfirmasi",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)== 0){
                    model.removeRow(SelectedRowIndex); 
                    total();
                    JOptionPane.showMessageDialog(this, "Barang Berhasil Di Hapus");                   
                }                   
            }
            
            radio();
    }
    
    public void hpssemua(){
        tableModel = (DefaultTableModel) tblpesanan.getModel();
        tableModel.setRowCount(0);
        totalharga.setText("Rp. 0,-");
        radio();
        total();
        jgntampil();
    }
    
    public void ulang(){
        hpssemua();
        listdaftar.isSelectionEmpty();
        jjumlah.setText(null);
        radio();
        nomortransaksi();
        totalharga.setText("Rp. 0,-");
        totalbayar.setText("Rp. 0,-");
        jdiskon.setText("0%");
        jmlhbyr.setText("Masukkan Jumlah Bayar");
        jbayar.setText(null);
        jgntampil();
        jkembalian.setText("Rp. 0,-");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdb = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ntrans = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listdaftar = new javax.swing.JList<>();
        jjumlah = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        tkn = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpesanan = new javax.swing.JTable();
        jbayar = new javax.swing.JTextField();
        jmlhbyr = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalharga = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jdiskon = new javax.swing.JLabel();
        totalbayar = new javax.swing.JLabel();
        jkembalian = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tunai = new javax.swing.JRadioButton();
        debit = new javax.swing.JRadioButton();
        temptot = new javax.swing.JLabel();
        dtl = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        haapus = new javax.swing.JLabel();
        ulng = new javax.swing.JLabel();
        metu = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        abt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Toko Buku TulisKu");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOKO BUKU TULISKU");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setText("Nomor Transaksi");

        ntrans.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        ntrans.setForeground(new java.awt.Color(0, 204, 255));
        ntrans.setText("jtext");

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daftar Barang", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Century Gothic", 0, 11), new java.awt.Color(204, 255, 255))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(0, 100, 100));
        jScrollPane1.setForeground(new java.awt.Color(0, 102, 102));

        listdaftar.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        listdaftar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Joyko Ballpoint (Black Ink)", "Joyko Pensil Kayu 2B", "Joyko Penghapus Pensil Mekanik", "Joyko Lem Stik", "Bantex Multiring Binder", "Aurora Laser Pointer", "SDI Spring Clip Flat Stainless Steel", "Paper One Kertas HVS A4 70", "Paper One Kertas HVS A4 80", "Deli Long Reach Stapler" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listdaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listdaftarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listdaftar);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, 177, 174));

        jjumlah.setBackground(new java.awt.Color(0, 102, 102));
        jjumlah.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jjumlah.setForeground(new java.awt.Color(255, 255, 255));
        jjumlah.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jjumlah.setText("jTextField1");
        jjumlah.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jjumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jjumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jjumlahKeyTyped(evt);
            }
        });
        jPanel2.add(jjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 78, 87, 30));

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 120, -1));

        tkn.setForeground(new java.awt.Color(255, 102, 102));
        tkn.setText("Tekan Enter");
        jPanel2.add(tkn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 64, -1, 20));

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaksi", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 11), new java.awt.Color(204, 255, 255))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblpesanan.setAutoCreateRowSorter(true);
        tblpesanan.setBackground(new java.awt.Color(255, 204, 153));
        tblpesanan.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        tblpesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Jenis Barang", "Harga Satuan", "Jumlah", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblpesanan);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 28, -1, 160));

        jbayar.setBackground(new java.awt.Color(0, 102, 102));
        jbayar.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jbayar.setForeground(new java.awt.Color(255, 255, 255));
        jbayar.setText("jTextField1");
        jbayar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbayarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jbayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbayarKeyTyped(evt);
            }
        });
        jPanel3.add(jbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 325, 180, 31));

        jmlhbyr.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jmlhbyr.setForeground(new java.awt.Color(255, 102, 102));
        jmlhbyr.setText("Masukkan Jumlah Bayar");
        jPanel3.add(jmlhbyr, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 308, -1, -1));

        jButton2.setText("Hapus Semua");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(372, 193, -1, -1));

        jButton3.setText("Hapus");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 193, -1, -1));

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Belanjaan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 11), new java.awt.Color(204, 255, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setText("Total Harga:");

        totalharga.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        totalharga.setForeground(new java.awt.Color(0, 204, 255));
        totalharga.setText("jtext");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setText("Diskon :");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setText("Total Bayar");

        jdiskon.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jdiskon.setText("0%");

        totalbayar.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        totalbayar.setForeground(new java.awt.Color(0, 204, 255));
        totalbayar.setText("jtext");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdiskon)
                    .addComponent(totalharga)
                    .addComponent(totalbayar))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalharga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jdiskon))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalbayar))
                .addContainerGap())
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 260, 130));

        jkembalian.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jkembalian.setForeground(new java.awt.Color(0, 204, 255));
        jkembalian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jkembalian.setText("Rp. 0,-");
        jPanel3.add(jkembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 119, -1));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Metode Pembayaran", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 11), new java.awt.Color(204, 255, 255))); // NOI18N

        tunai.setBackground(new java.awt.Color(0, 102, 102));
        rdb.add(tunai);
        tunai.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        tunai.setForeground(new java.awt.Color(255, 255, 255));
        tunai.setText("Tunai");
        tunai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tunai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tunaiActionPerformed(evt);
            }
        });

        debit.setBackground(new java.awt.Color(0, 102, 102));
        rdb.add(debit);
        debit.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        debit.setForeground(new java.awt.Color(255, 255, 255));
        debit.setText("Debit");
        debit.setAutoscrolls(true);
        debit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        debit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tunai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(debit)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tunai)
                    .addComponent(debit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 242, -1, 60));

        temptot.setText("jLabel4");
        jPanel3.add(temptot, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 197, -1, -1));

        dtl.setForeground(new java.awt.Color(255, 255, 102));
        dtl.setText("Menghapus Seluruh isi Tabel");
        jPanel3.add(dtl, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 222, -1, -1));

        jButton4.setText("Ulang");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 193, -1, -1));

        jButton5.setText("Keluar");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton5MouseExited(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 193, -1, -1));

        haapus.setForeground(new java.awt.Color(255, 255, 102));
        haapus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        haapus.setText("jLabel7");
        jPanel3.add(haapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, -1, -1));

        ulng.setForeground(new java.awt.Color(255, 255, 102));
        ulng.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ulng.setText("jLabel7");
        jPanel3.add(ulng, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, -1, -1));

        metu.setForeground(new java.awt.Color(255, 204, 204));
        metu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        metu.setText("jLabel7");
        jPanel3.add(metu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel7.setText("Kembalian :");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 364, -1, -1));

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ketentuan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 11), new java.awt.Color(204, 255, 255))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 255, 255));
        jLabel8.setText("1.");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 255, 255));
        jLabel9.setText("Jika membeli lebih dari satu Aurora Laser Printer, harga");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 255, 255));
        jLabel10.setText("satuannya menjadi Rp. 100.000,-");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 255, 255));
        jLabel11.setText("2.");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 255, 255));
        jLabel12.setText("Pembayaran melalui Debit mendapatkan potongan ");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 255, 255));
        jLabel13.setText("harga 10% dari total harga");
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 255, 255));
        jLabel14.setText("3.");
        jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 255, 255));
        jLabel15.setText("Maksimal membeli 50 untuk satu jenis barang");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        abt.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        abt.setForeground(new java.awt.Color(204, 255, 255));
        abt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        abt.setText("ABOUT ME");
        abt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        abt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                abtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                abtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(ntrans))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(abt, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addComponent(ntrans))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(abt))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 898, 522));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        transaksi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jjumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jjumlahKeyTyped
        type = evt.getKeyChar();
        if (jjumlah.getText().length() > 1 || !Character.isDigit(type)){
            evt.consume();
        }
    }//GEN-LAST:event_jjumlahKeyTyped

    private void jbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbayarKeyReleased
        type =evt.getKeyChar();
        
        if (!jbayar.getText().isEmpty()){
            jmlhbyr.setText("Tekan Enter");
        }
        if (jbayar.getText().isEmpty()){
            jmlhbyr.setText("Masukkan Jumlah Bayar");
        }
       
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            long thrg = Long.parseLong(temptot.getText());
            long byr = Long.parseLong(jbayar.getText());
            long kbl = byr - thrg;
            String ab = "<html><font color=\"rgb(0,204,255)\"><font size=\"4\"><b>"+"Rp. "+df.format(kbl)+"-,</b></font></font></html>";
            if (kbl > 0){
                jkembalian.setText(ab);
                jbayar.requestFocus();
                 
            } if(kbl < 0){
                JOptionPane.showMessageDialog(null, "Uang Yang dimasukkan Kurang, silahkan Input Kembali","Info",JOptionPane.OK_OPTION);
                jbayar.setText("");
                jbayar.requestFocus();
            } if  (kbl == 0){
                String ee ="<html><font color=\"rgb(210,113,172)\"><font size=\"9\"><b>Terimakasih</b></font></font></html>";
                jkembalian.setText("Rp. 0,-");
                JOptionPane.showMessageDialog(null,ee +"\nUang Pas","Info",JOptionPane.CLOSED_OPTION);
                this.requestFocus();
            } if (jbayar.getText().isEmpty()){
                System.out.println("Kosong");
            }
        } 

         //<font size="6">This is some text!</font>
         //<font color="rgb(0,204,255)">This is some text!</font>
         //<html><b>This is some text!</b></html>
         //0,204,255
//        long total = 0;
      
    }//GEN-LAST:event_jbayarKeyReleased

    private void jbayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbayarKeyPressed
        
        
    }//GEN-LAST:event_jbayarKeyPressed

    private void jbayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbayarKeyTyped
        type = evt.getKeyChar();
        if (!Character.isDigit(type)){
            evt.consume();
        }
        
    }//GEN-LAST:event_jbayarKeyTyped

    private void tunaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tunaiActionPerformed
        if (tunai.isSelected()){
            tunai();
        }
        if (!tunai.isSelected()){
            tunai.setBackground(new java.awt.Color(0,102,102));
        }
    }//GEN-LAST:event_tunaiActionPerformed

    private void debitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debitActionPerformed
        if (debit.isSelected()){
            debit();
        }
         if (!debit.isSelected()){
            debit.setBackground(new java.awt.Color(0,102,102));
         }
    }//GEN-LAST:event_debitActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        hpsbaris();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        dtl.setVisible(true);
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        dtl.setVisible(false);
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Anda ingin menghapus seluruh isi tabel?","Konfirmasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0){
             hpssemua();
        } else;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Anda ingin mengulang seluruh inputan?","Konfirmasi",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==0){
            ulang();
        } else;
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (JOptionPane.showConfirmDialog(null,"Anda yakin ingin Keluar?","Konfirmasi",JOptionPane.OK_CANCEL_OPTION) == 0){
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void abtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abtMouseClicked
        About a = new About();
        a.show();
    }//GEN-LAST:event_abtMouseClicked

    private void abtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abtMouseEntered
        String ab = "<html><font color=\"rgb(255,255,102)\"><b>ABOUT ME</b></font></html>";
        abt.setText(ab);
    }//GEN-LAST:event_abtMouseEntered

    private void abtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abtMouseExited
        String ab = "<html><font color=\"rgb(204,255,255)\"><b>ABOUT ME</b></font></html>";
        abt.setText(ab);
    }//GEN-LAST:event_abtMouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        haapus.setVisible(true);
        haapus.setText("Menghapus Satu jenis Belanjaan");
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        haapus.setVisible(false);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
       ulng.setText("Mengosongkan seluruh inputan. Mengganti nomor transaksi baru.");
       ulng.setVisible(true);
    }//GEN-LAST:event_jButton4MouseEntered

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseExited
        ulng.setVisible(false);
    }//GEN-LAST:event_jButton4MouseExited

    private void jButton5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseEntered
        metu.setText("Keluar Dari Aplikasi");
        metu.setVisible(true);
    }//GEN-LAST:event_jButton5MouseEntered

    private void jButton5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseExited
        metu.setVisible(false);
    }//GEN-LAST:event_jButton5MouseExited

    private void listdaftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listdaftarMouseClicked
        jjumlah.requestFocus();
    }//GEN-LAST:event_listdaftarMouseClicked

    private void jjumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jjumlahKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            transaksi();
        }
        if (!jjumlah.getText().isEmpty()){
            tkn.setVisible(true);
        }
        else if (jjumlah.getText().isEmpty()){
            tkn.setVisible(false);
        } 
    }//GEN-LAST:event_jjumlahKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TulisKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TulisKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TulisKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TulisKu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TulisKu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abt;
    private javax.swing.JRadioButton debit;
    private javax.swing.JLabel dtl;
    private javax.swing.JLabel haapus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jbayar;
    private javax.swing.JLabel jdiskon;
    private javax.swing.JTextField jjumlah;
    private javax.swing.JLabel jkembalian;
    private javax.swing.JLabel jmlhbyr;
    private javax.swing.JList<String> listdaftar;
    private javax.swing.JLabel metu;
    private javax.swing.JLabel ntrans;
    private javax.swing.ButtonGroup rdb;
    private javax.swing.JTable tblpesanan;
    private javax.swing.JLabel temptot;
    private javax.swing.JLabel tkn;
    private javax.swing.JLabel totalbayar;
    private javax.swing.JLabel totalharga;
    private javax.swing.JRadioButton tunai;
    private javax.swing.JLabel ulng;
    // End of variables declaration//GEN-END:variables
}
