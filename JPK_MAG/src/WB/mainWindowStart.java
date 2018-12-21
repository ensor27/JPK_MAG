package WB;
// main jpk_mag
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.DOMException;

//import com.itextpdf.text.DocumentException;


//import PDF.cashPlan;
import XML.jpk_mag;

public class mainWindowStart extends JFrame {

	private JPanel contentPane;
	private JLabel lblWprowadOczekiwanLiczb;

	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindowStart frame = new mainWindowStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
	
	
	
	
	/**
	 * Create the frame.
	 */
	public mainWindowStart() {
		setResizable(false);
		setTitle("JPK_MAG              CUB4U");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton analizaGodzin = new JButton("Start JPK");
		analizaGodzin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		analizaGodzin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				
						System.out.println("Start JPK_MAG");
						try {
							try {
								jpk_mag.main();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (DOMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("JPK_MAG done");
						System.exit(0);
					
			}
		});
		analizaGodzin.setBounds(89, 287, 232, 38);
		contentPane.add(analizaGodzin);
		//Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-383, -402, 812, 812);
		contentPane.add(lblNewLabel);
	}
	
	private static boolean checkDatePattern(String data) {
	    try {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        format.parse(data);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
}