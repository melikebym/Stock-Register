using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Net.Configuration;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace StokKayit_W12
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void groupBox1_Enter(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            listele();
        }

        private void listele() //veritabanındaki kayıtların görüntülenmesi
        {
            baglanti.Open();
            SqlDataAdapter da = new SqlDataAdapter("Select * from Malzemeler",baglanti);
            DataTable tablo = new DataTable();
            da.Fill(tablo);
            dataGridView1.DataSource = tablo;
            baglanti.Close();
        }

        SqlConnection baglanti = new SqlConnection(" Data Source = MELIKE\\SQLEXPRESS ; Initial Catalog= StokKayit ; Integrated Security =True"); 
        private void button1_Click(object sender, EventArgs e)
        { //ekle
            String t1 = textBox1.Text;
            String t2 = textBox2.Text;
            String t3 = textBox3.Text;
            String t4 = textBox4.Text;
            String t5 = textBox5.Text;
            String t6 = textBox6.Text;

            baglanti.Open();
            SqlCommand komut = new SqlCommand(" INSERT INTO Malzemeler(MazemeKodu, MalzemeAdi, YillikSatis, BirimFiyat, MinStok, TSuresi) VALUES ('"+t1+ "','"+t2+"','"+t3+"','"+t4+"','"+t5+"','"+t6+"')", baglanti);
            komut.ExecuteNonQuery(); //komutu gerçekleştir demek
            baglanti.Close() ;
            listele();
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            textBox1.Text = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            textBox2.Text = dataGridView1.CurrentRow.Cells[1].Value.ToString();
            textBox3.Text = dataGridView1.CurrentRow.Cells[2].Value.ToString();
            textBox4.Text = dataGridView1.CurrentRow.Cells[3].Value.ToString();
            textBox5.Text = dataGridView1.CurrentRow.Cells[4].Value.ToString();
            textBox6.Text = dataGridView1.CurrentRow.Cells[5].Value.ToString();

        }

        private void button2_Click(object sender, EventArgs e)
        {
            String t1 = textBox1.Text; //malzeme kodu bilgisini al
            baglanti.Open() ;
            SqlCommand komut = new SqlCommand(" DELETE FROM Malzemeler WHERE MazemeKodu=('"+t1+"')",baglanti);
            komut.ExecuteNonQuery();
            baglanti.Close() ;
            listele();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            String t1 = textBox1.Text;
            String t2 = textBox2.Text;
            String t3 = textBox3.Text;
            String t4 = textBox4.Text;
            String t5 = textBox5.Text;
            String t6 = textBox6.Text;
            baglanti.Open();
            SqlCommand komut = new SqlCommand("UPDATE Malzemeler SET MazemeKodu='" + t1 + "',MalzemeAdi='" + t2 + "',YillikSatis='"+t3+"',BirimFiyat='"+t4+"',MinStok='"+t5+"',TSuresi='"+t6+"' WHERE MazemeKodu='"+t1+"'   " ,baglanti);
            komut.ExecuteNonQuery();
            baglanti.Close() ;
            listele();

        }
    }
}
