
package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class KauppaTest {
    
    Pankki pankki;
    Viitegeneraattori vk;
    Varasto varasto;
    
    @Before
    public void setUp(){
        pankki = mock(Pankki.class);
        vk = mock(Viitegeneraattori.class);
        when(vk.uusi()).thenReturn(55);
        varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(5);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "laku", 5));
    }
    
    @Test
    public void ostosYhdellaTuotteella(){
        Kauppa x = new Kauppa(varasto, pankki, vk);
        x.aloitaAsiointi();
        x.lisaaKoriin(1);
        x.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
    }
    
    @Test
    public void ostosKahdellaEriTuotteella(){
        when(varasto.saldo(2)).thenReturn(5);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "maito", 2));
        
        Kauppa x = new Kauppa(varasto, pankki, vk);
        x.aloitaAsiointi();
        x.lisaaKoriin(1);
        x.lisaaKoriin(2);
        
        x.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(7));
    }
    
    @Test
    public void ostosKahdellaSamallaTuotteella(){
        Kauppa x = new Kauppa(varasto, pankki, vk);
        x.aloitaAsiointi();
        x.lisaaKoriin(1);
        x.lisaaKoriin(1);
        
        x.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(10));
        
    }
    
    @Test
    public void ostosKunToinenTuoteLoppu(){
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "maito", 2));
        
        Kauppa x = new Kauppa(varasto, pankki, vk);
        x.aloitaAsiointi();
        x.lisaaKoriin(1);
        x.lisaaKoriin(2);
        
        x.tilimaksu("erkki", "112558");
        
        verify(pankki).tilisiirto(eq("erkki"), eq(55), eq("112558"), eq("33333-44455"), eq(5));
    }
    
}
